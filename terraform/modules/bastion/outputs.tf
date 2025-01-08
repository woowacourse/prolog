output "bastion_sg_id" {
    description = "The ID of the Security Group for the Bastion Host"
    value       = aws_security_group.bastion_sg.id
}

output "bastion_sg_name" {
    description = "The Name tag of the Security Group for the Bastion Host"
    value       = aws_security_group.bastion_sg.tags["Name"]
}

output "bastion_instance_id" {
    description = "The ID of the Bastion Host instance"
    value       = aws_instance.bastion.id
}

output "bastion_instance_public_ip" {
    description = "The public IP address of the Bastion Host"
    value       = aws_eip.bastion_eip.public_ip
}

output "bastion_instance_private_ip" {
    description = "The private IP address of the Bastion Host"
    value       = aws_instance.bastion.private_ip
}

output "bastion_eip_allocation_id" {
    description = "The Allocation ID of the Elastic IP associated with the Bastion Host"
    value       = aws_eip.bastion_eip.id
}

output "bastion_instance_arn" {
    description = "The ARN of the Bastion Host instance"
    value       = aws_instance.bastion.arn
}
