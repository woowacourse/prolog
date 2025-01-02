variable "vpc_id" {}
variable "project_name" {}
variable "db_name" {}
variable "secret_name" {}
variable "ingress_security_group_ids" {
    type = list(string)
}
variable "private_subnet_ids" {
    type = list(string)
}
variable "server_tags" {
    type = map(string)
}
variable "database_tags" {
    type = map(string)
}
