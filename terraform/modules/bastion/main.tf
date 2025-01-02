resource "aws_security_group" "bastion_sg" {
    vpc_id = var.vpc_id

    ingress {
        from_port   = 22
        to_port     = 22
        protocol    = "tcp"
        cidr_blocks = ["58.76.251.0/24"]
        description = "for VPN"
    }

    egress {
        from_port = 0
        to_port   = 0
        protocol  = "-1"
        cidr_blocks = ["0.0.0.0/0"]
    }

    tags = merge(var.server_tags, {
        Name = "${var.project_name}_bastion_sg"
    })
}

resource "aws_eip" "bastion_eip" {
    tags = merge(var.server_tags, {
        Name = "${var.project_name}_bastion_eip"
    })
}

resource "aws_instance" "bastion" {
    ami           = var.ami_id
    instance_type = "t4g.micro"
    subnet_id = element(var.public_subnet_ids, 0)
    vpc_security_group_ids = [aws_security_group.bastion_sg.id]
    key_name      = var.key_pair_name

    user_data = <<-EOF
      #!/bin/bash
      sudo timedatectl set-timezone Asia/Seoul
      EOF

    depends_on = [aws_eip.bastion_eip]

    tags = merge(var.server_tags, {
        Name = "${var.project_name}_bastion"
    })
}

resource "aws_eip_association" "bastion_eip" {
    instance_id   = aws_instance.bastion.id
    allocation_id = aws_eip.bastion_eip.id
}
