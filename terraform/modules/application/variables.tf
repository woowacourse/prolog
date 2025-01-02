variable "vpc_id" {}
variable "project_name" {}
variable "environment" {}
variable "ec2_role_name" {}
variable "bucket_name" {}
variable "region" {}
variable "code_deploy_role_arn" {}
variable "ami_id" {}
variable "key_pair_name" {}
variable "bastion_sg_id" {}
variable "private_subnet_ids" {
    type = list(string)
}
variable "public_subnet_ids" {
    type = list(string)
}
variable "service_worker_tags" {
    type = map(string)
}
variable "server_tags" {
    type = map(string)
}
