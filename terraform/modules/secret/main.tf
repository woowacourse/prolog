data "aws_secretsmanager_secret" "secret" {
    name = var.secret_name
}

data "aws_secretsmanager_secret_version" "secret_version" {
    secret_id = data.aws_secretsmanager_secret.secret.id
}
