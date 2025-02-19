# Network
output "vpc_id" {
  description = "VPC ID created by the network module"
  value       = module.network.vpc_id
}

output "public_subnet_ids" {
  description = "Public subnet IDs created by the network module"
  value       = module.network.public_subnet_ids
}

output "private_subnet_ids" {
  description = "Private subnet IDs created by the network module"
  value       = module.network.private_subnet_ids
}

# Storage
output "bucket_arn" {
  description = "Bucket ARN created by the storage module"
  value       = module.storage.bucket_arn
}

output "bucket_name" {
  description = "Bucket name created by the storage module"
  value       = module.storage.bucket_name
}

# IAM
output "ec2_role_name" {
  description = "EC2 IAM role name created by the IAM module"
  value       = module.iam.ec2_role_name
}

output "s3_policy_arn" {
  description = "S3 access policy ARN created by the IAM module"
  value       = module.iam.s3_access_policy_arn
}

# Bastion
output "bastion_sg_id" {
  description = "Security Group ID for the Bastion host"
  value       = module.bastion.bastion_sg_id
}

output "bastion_eip" {
  description = "Elastic IP address for the Bastion host"
  value       = module.bastion.bastion_eip_allocation_id
}

# Application
output "application_sg_id" {
  description = "Security Group ID for the application instances"
  value       = module.application.application_sg_id
}

output "application_asg_name" {
  description = "Name of the Auto Scaling Group for application instances"
  value       = module.application.asg_name
}

output "application_launch_template_id" {
  description = "Launch Template ID for application instances"
  value       = module.application.launch_template_id
}

# Database
output "database_endpoint" {
  description = "Endpoint of the RDS database"
  value       = module.database.db_instance_endpoint
}

output "database_id" {
  description = "ID of the RDS database instance"
  value       = module.database.db_instance_id
}

output "database_sg_id" {
  description = "Security Group ID for the database"
  value       = module.database.database_sg_id
}
