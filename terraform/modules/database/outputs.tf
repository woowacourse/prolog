output "database_sg_id" {
    description = "Security Group ID for the database"
    value       = aws_security_group.database_sg.id
}

output "database_sg_name" {
    description = "Name of the Security Group for the database"
    value       = aws_security_group.database_sg.tags["Name"]
}

output "db_subnet_group_name" {
    description = "Name of the DB Subnet Group"
    value       = aws_db_subnet_group.database_subnet_group.name
}

output "db_instance_id" {
    description = "ID of the DB instance"
    value       = aws_db_instance.database.id
}

output "db_instance_arn" {
    description = "ARN of the DB instance"
    value       = aws_db_instance.database.arn
}

output "db_instance_endpoint" {
    description = "Endpoint of the DB instance"
    value       = aws_db_instance.database.endpoint
}

output "db_instance_username" {
    description = "Username for the DB instance"
    value       = aws_db_instance.database.username
}

output "db_instance_name" {
    description = "Name of the database created on the DB instance"
    value       = aws_db_instance.database.db_name
}
