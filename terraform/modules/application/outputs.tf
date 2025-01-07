output "alb_sg_id" {
    description = "Security Group ID for the Application Load Balancer"
    value       = aws_security_group.alb_sg.id
}

output "alb_arn" {
    description = "ARN of the Application Load Balancer"
    value       = aws_lb.alb.arn
}

output "alb_dns_name" {
    description = "DNS name of the Application Load Balancer"
    value       = aws_lb.alb.dns_name
}

output "alb_target_group_arn" {
    description = "ARN of the Target Group"
    value       = aws_lb_target_group.tg.arn
}


output "alb_http_listener_arn" {
    description = "ARN of the HTTP Listener"
    value       = aws_lb_listener.lb_http.arn
}

output "alb_https_listener_arn" {
    description = "ARN of the HTTPS Listener"
    value       = aws_lb_listener.lb_https.arn
}

output "asg_name" {
    description = "Name of the Auto Scaling Group"
    value       = aws_autoscaling_group.asg.name
}

output "asg_arn" {
    description = "ARN of the Auto Scaling Group"
    value       = aws_autoscaling_group.asg.arn
}

output "application_sg_id" {
    description = "Security Group ID for the application instances"
    value       = aws_security_group.application_sg.id
}

output "launch_template_id" {
    description = "ID of the EC2 Launch Template"
    value       = aws_launch_template.ec2_launch_template.id
}

output "launch_template_name" {
    description = "Name of the EC2 Launch Template"
    value       = aws_launch_template.ec2_launch_template.name
}

output "ami_id" {
    description = "ID of the AMI used in the Launch Template"
    value       = data.aws_ami.ec2_ami.id
}

output "certificate_arn" {
    description = "ARN of the ACM certificate used for HTTPS Listener"
    value       = data.aws_acm_certificate.certificate.arn
}
