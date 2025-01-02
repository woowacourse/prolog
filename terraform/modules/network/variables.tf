variable "region" {}
variable "project_name" {}
variable "server_tags" {
    type = map(string)
}
variable "gateway_tags" {
    type = map(string)
}
