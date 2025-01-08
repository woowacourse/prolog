data "aws_ami" "ec2_ami" {
    most_recent = true
    owners = ["amazon"]
    filter {
        name = "name"
        values = ["amzn2-ami-hvm-*-arm64-gp2"]
    }
}

resource "tls_private_key" "private_key" {
    algorithm = "RSA"
    rsa_bits  = 2048
}

resource "aws_key_pair" "key_pair" {
    key_name   = "${var.project_name}_key_pair"
    public_key = tls_private_key.private_key.public_key_openssh

    // Note: Tag not required
}

resource "local_file" "private_key" {
    filename        = "${path.root}/keys/private_key.pem"
    content         = tls_private_key.private_key.private_key_pem
    file_permission = "0600"
}
