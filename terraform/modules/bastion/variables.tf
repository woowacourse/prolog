variable "vpc_id" {}
variable "project_name" {}
variable "ami_id" {}
variable "key_pair_name" {}
variable "public_subnet_ids" {
    type = list(string)
}
variable "server_tags" {
    type = map(string)
}
