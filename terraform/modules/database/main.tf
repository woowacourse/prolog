module "secret" {
    source = "../../modules/secret"

    secret_name = var.secret_name
}

locals {
    username = module.secret.value["spring.datasource.username"]
    password = module.secret.value["spring.datasource.password"]
}

resource "aws_security_group" "database_sg" {
    vpc_id = var.vpc_id

    ingress {
        from_port       = 3306
        to_port         = 3306
        protocol        = "tcp"
        security_groups = var.ingress_security_group_ids
    }

    egress {
        from_port = 0
        to_port   = 0
        protocol  = "-1"
        cidr_blocks = ["0.0.0.0/0"]
    }
    tags = merge(var.server_tags, {
        Name = "${var.project_name}_database_sg"
    })
}

resource "aws_db_subnet_group" "database_subnet_group" {
    name       = "${var.project_name}-subnet-group"
    subnet_ids = var.private_subnet_ids

    // Note: Tag not required
}

resource "aws_db_instance" "database" {
    identifier           = var.project_name
    allocated_storage    = 20
    engine               = "mysql"
    engine_version       = "8.0"
    instance_class       = "db.t4g.micro"
    db_name              = var.db_name
    username             = local.username
    password             = local.password
    parameter_group_name = "default.mysql8.0"
    skip_final_snapshot  = true
    publicly_accessible  = false
    vpc_security_group_ids = [aws_security_group.database_sg.id]
    db_subnet_group_name = aws_db_subnet_group.database_subnet_group.name

    tags = merge(var.database_tags, {
        Name = "${var.project_name}_database"
    })
}
