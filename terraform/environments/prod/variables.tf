variable "region" {
    default = "ap-northeast-2"
}
variable "project_name" {
    default = "prolog-prod"
}
variable "environment" {
    default = "prod"
}
variable "bucket_name" {
    default = "prolog-prod-bucket"
}
variable "key_pair_name" {
    default = "prolog-prod"
}
variable "db_name" {
    default = "prolog"
}
variable "db_secret_name" {
    default = "secrets/prolog_prod"
}
