terraform {
    required_providers {
        aws = {
            source  = "hashicorp/aws"
            version = "5.54.1"
        }
    }

    cloud {
        organization = "cholog"

        workspaces {
            name = "cholog-dev"
        }
    }
}

provider "aws" {
    region = var.region

    /** Note: access_key와 secret_key는 환경변수를 통해 설정하면 된다.
    export AWS_ACCESS_KEY_ID="your-access-key"
    export AWS_SECRET_ACCESS_KEY="your-secret-key"
     */
}

module "tags" {
    source = "../../modules/tags"

    project_name = var.project_name
    environment  = var.environment
}

module "compute" {
    source = "../../modules/compute"

    project_name = var.project_name
}


module "network" {
    source = "../../modules/network"

    region       = var.region
    project_name = var.project_name
    server_tags  = module.tags.server_tags
    gateway_tags = module.tags.gateway_tags
}

module "storage" {
    source = "../../modules/storage"

    bucket_name  = var.bucket_name
    project_name = var.project_name
    storage_tags = module.tags.storage_tags
}

module "iam" {
    source = "../../modules/iam"

    project_name = var.project_name
    bucket_arns = [
        module.storage.bucket_arn,
        "${module.storage.bucket_arn}/*"
    ]
}

module "bastion" {
    source = "../../modules/bastion"

    vpc_id            = module.network.vpc_id
    project_name      = var.project_name
    ami_id            = module.compute.ami_id
    key_pair_name     = module.compute.key_pair_name
    public_subnet_ids = module.network.public_subnet_ids
    server_tags       = module.tags.server_tags

}

module "application" {
    source = "../../modules/application"

    vpc_id               = module.network.vpc_id
    project_name         = var.project_name
    environment          = var.environment
    ec2_role_name        = module.iam.ec2_role_name
    bucket_name          = module.storage.bucket_name
    region               = var.region
    code_deploy_role_arn = module.iam.code_deploy_role_arn
    ami_id               = module.compute.ami_id
    key_pair_name        = module.compute.key_pair_name
    bastion_sg_id        = module.bastion.bastion_sg_id
    private_subnet_ids   = module.network.private_subnet_ids
    public_subnet_ids    = module.network.public_subnet_ids
    service_worker_tags  = module.tags.service_worker_tags
    server_tags          = module.tags.server_tags
}

module "database" {
    source = "../../modules/database"

    vpc_id       = module.network.vpc_id
    project_name = var.project_name
    db_name      = var.db_name
    secret_name  = var.db_secret_name
    ingress_security_group_ids = [module.application.application_sg_id, module.bastion.bastion_sg_id]

    private_subnet_ids = module.network.private_subnet_ids
    server_tags        = module.tags.server_tags
    database_tags      = module.tags.database_tags
}


