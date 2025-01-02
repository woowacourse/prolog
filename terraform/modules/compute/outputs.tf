output "ami_id" {
    description = "The ID of the most recent Amazon Linux 2 AMI"
    value       = data.aws_ami.ec2_ami.id
}

output "ami_name" {
    description = "The name of the most recent Amazon Linux 2 AMI"
    value       = data.aws_ami.ec2_ami.name
}

output "ami_owner_id" {
    description = "The owner ID of the AMI"
    value       = data.aws_ami.ec2_ami.owner_id
}

output "ami_creation_date" {
    description = "The creation date of the most recent Amazon Linux 2 AMI"
    value       = data.aws_ami.ec2_ami.creation_date
}

output "key_pair_name" {
    description = "Name of the EC2 Key Pair"
    value       = aws_key_pair.key_pair.key_name
}

output "private_key_file" {
    description = "Path to the private key file generated locally"
    value       = local_file.private_key.filename
}
