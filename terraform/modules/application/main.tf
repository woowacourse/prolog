resource "aws_security_group" "alb_sg" {
    vpc_id = var.vpc_id

    ingress {
        from_port   = 80
        to_port     = 80
        protocol    = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
        description = "Allow HTTP traffic"
    }
    ingress {
        from_port   = 443
        to_port     = 443
        protocol    = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
        description = "Allow HTTPS traffic"
    }

    egress {
        from_port = 0
        to_port   = 0
        protocol  = "-1"
        cidr_blocks = ["0.0.0.0/0"]
    }

    tags = merge(var.server_tags, {
        Name = "${var.project_name}_alb_sg"
    })
}

resource "aws_lb" "alb" {
    name               = "${var.project_name}-alb"
    internal           = false
    load_balancer_type = "application"
    security_groups = [aws_security_group.alb_sg.id]
    subnets            = var.public_subnet_ids

    tags = merge(var.service_worker_tags, {
        Name = "${var.project_name}_alb"
    })
}

resource "aws_lb_target_group" "tg_blue" {
    name     = "${var.project_name}-tg-blue"
    port     = 80
    protocol = "HTTP"
    vpc_id   = var.vpc_id

    tags = merge(var.service_worker_tags, {
        Name = "${var.project_name}_tg_blue"
    })
}

resource "aws_lb_target_group" "tg_green" {
    name     = "${var.project_name}-tg-green"
    port     = 80
    protocol = "HTTP"
    vpc_id   = var.vpc_id

    tags = merge(var.service_worker_tags, {
        Name = "${var.project_name}_tg_green"
    })
}

resource "aws_lb_listener" "lb_http" {
    load_balancer_arn = aws_lb.alb.arn
    port              = 80
    protocol          = "HTTP"

    default_action {
        type = "redirect"

        redirect {
            protocol    = "HTTPS"
            port        = "443"
            status_code = "HTTP_301"
        }
    }

    tags = merge(var.service_worker_tags, {
        Name = "${var.project_name}_lb_http"
    })
}

// Note: 기존에 있는 인증서 사용
data "aws_acm_certificate" "certificate" {
    domain      = "prolog.techcourse.co.kr"
    statuses = ["ISSUED"]
    most_recent = true
}

resource "aws_lb_listener" "lb_https" {
    load_balancer_arn = aws_lb.alb.arn
    port              = 443
    protocol          = "HTTPS"
    ssl_policy        = "ELBSecurityPolicy-TLS13-1-2-2021-06"
    certificate_arn   = data.aws_acm_certificate.certificate.arn

    default_action {
        type             = "forward"
        target_group_arn = aws_lb_target_group.tg_blue.arn
    }

    tags = merge(var.service_worker_tags, {
        Name = "${var.project_name}_lb_https"
    })
}

resource "aws_autoscaling_group" "asg" {
    launch_template {
        id      = aws_launch_template.ec2_launch_template.id
        version = "$Latest"
    }

    max_size         = 4
    min_size         = 1
    desired_capacity = 1
    vpc_zone_identifier = var.private_subnet_ids

    // Note: 이후 target_group_arns는 CodeDeploy에서 알아서 관리 예정
    target_group_arns = [
        aws_lb_target_group.tg_blue.arn,
        aws_lb_target_group.tg_green.arn
    ]

    dynamic "tag" {
        for_each = var.server_tags
        content {
            key                 = tag.key
            value               = tag.value
            propagate_at_launch = true
        }
    }
    tag {
        key                 = "Environment"
        value               = var.environment
        propagate_at_launch = true
    }
    tag {
        key                 = "Name"
        value               = var.project_name
        propagate_at_launch = true
    }
}

data "aws_ami" "ec2_ami" {
    most_recent = true
    owners = ["amazon"]
    filter {
        name = "name"
        values = ["amzn2-ami-hvm-*-arm64-gp2"]
    }
}

resource "aws_security_group" "application_sg" {
    vpc_id = var.vpc_id

    ingress {
        from_port = 8080
        to_port   = 8080
        protocol  = "tcp"
        security_groups = [aws_security_group.alb_sg.id]
    }

    ingress {
        from_port = 22
        to_port   = 22
        protocol  = "tcp"
        security_groups = [var.bastion_sg_id]
    }

    egress {
        from_port = 0
        to_port   = 0
        protocol  = "-1"
        cidr_blocks = ["0.0.0.0/0"]
    }

    tags = merge(var.server_tags, {
        Name = "${var.project_name}_application_sg"
    })
}

resource "aws_iam_instance_profile" "ec2_instance_profile" {
    name = "${var.project_name}-ec2-instance-profile"
    role = var.ec2_role_name

    tags = merge(var.service_worker_tags, {
        Name = "${var.project_name}_ec2instance_profile"
    })
}

resource "aws_launch_template" "ec2_launch_template" {
    name          = "${var.project_name}-ec2-launch-template"
    image_id      = var.ami_id
    instance_type = "t4g.small"
    key_name      = var.key_pair_name

    user_data = base64encode(templatefile("${path.module}/templates/user_data.tpl", {
        environment = var.environment,
        bucket_name = var.bucket_name,
        region      = var.region
    }))

    network_interfaces {
        security_groups = [aws_security_group.application_sg.id]
        associate_public_ip_address = false
    }

    iam_instance_profile {
        name = aws_iam_instance_profile.ec2_instance_profile.name
    }

    monitoring {
        enabled = true
    }

    tags = merge(var.service_worker_tags, {
        Name = "${var.project_name}_ec2_launch_template"
    })
}

resource "aws_codedeploy_app" "deploy" {
    name = "${var.project_name}-code-deploy"

    tags = merge(var.server_tags, {
        Name = "${var.project_name}_code_deploy"
    })
}

resource "aws_codedeploy_deployment_group" "deployment_group" {
    app_name              = aws_codedeploy_app.deploy.name
    deployment_group_name = var.project_name
    service_role_arn      = var.code_deploy_role_arn

    autoscaling_groups = [aws_autoscaling_group.asg.name]

    deployment_style {
        deployment_type = "IN_PLACE"
    }

    auto_rollback_configuration {
        enabled = true
        events = ["DEPLOYMENT_FAILURE"]
    }
}

/*
resource "aws_codedeploy_deployment_group" "deployment_group" {
    app_name              = aws_codedeploy_app.deploy.name
    deployment_group_name = var.project_name
    service_role_arn      = var.code_deploy_role_arn

    autoscaling_groups = [aws_autoscaling_group.asg.name]

    deployment_style {
        deployment_type   = "BLUE_GREEN"
        deployment_option = "WITH_TRAFFIC_CONTROL"
    }

    auto_rollback_configuration {
        enabled = true
        events = ["DEPLOYMENT_FAILURE"]
    }

    blue_green_deployment_config {
        terminate_blue_instances_on_deployment_success {
            action                           = "TERMINATE"
            termination_wait_time_in_minutes = 10
        }

        deployment_ready_option {
            action_on_timeout    = "STOP_DEPLOYMENT"
            wait_time_in_minutes = 10
        }
    }

    load_balancer_info {
        target_group_pair_info {
            target_group {
                name = aws_lb_target_group.tg_blue.name
            }
            target_group {
                name = aws_lb_target_group.tg_green.name
            }
            prod_traffic_route {
                listener_arns = [aws_lb_listener.lb_https.arn]
            }
        }
    }
}
 */
