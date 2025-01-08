// VPC
output "vpc_id" {
    description = "The ID of the created VPC"
    value       = aws_vpc.vpc.id
}

output "vpc_cidr_block" {
    description = "The CIDR block of the created VPC"
    value       = aws_vpc.vpc.cidr_block
}

output "vpc_arn" {
    description = "The ARN of the created VPC"
    value       = aws_vpc.vpc.arn
}

output "vpc_dns_support" {
    description = "Whether DNS support is enabled for the VPC"
    value       = aws_vpc.vpc.enable_dns_support
}

output "vpc_dns_hostnames" {
    description = "Whether DNS hostnames are enabled for the VPC"
    value       = aws_vpc.vpc.enable_dns_hostnames
}

output "vpc_tags" {
    description = "Tags applied to the VPC"
    value       = aws_vpc.vpc.tags
}

// Public Subnet
output "public_subnet_a_id" {
    description = "The ID of the public subnet in availability zone A"
    value       = aws_subnet.public_a.id
}

output "public_subnet_b_id" {
    description = "The ID of the public subnet in availability zone B"
    value       = aws_subnet.public_b.id
}

output "public_subnet_ids" {
    description = "A list of all public subnet IDs"
    value = [aws_subnet.public_a.id, aws_subnet.public_b.id]
}

// Private Subnet
output "private_subnet_a_id" {
    description = "The ID of the private subnet in availability zone A"
    value       = aws_subnet.private_a.id
}

output "private_subnet_b_id" {
    description = "The ID of the private subnet in availability zone B"
    value       = aws_subnet.private_b.id
}

output "private_subnet_ids" {
    description = "A list of all private subnet IDs"
    value = [aws_subnet.private_a.id, aws_subnet.private_b.id]
}

output "all_subnet_ids" {
    description = "A list of all subnet IDs (both public and private)"
    value = [
        aws_subnet.public_a.id,
        aws_subnet.public_b.id,
        aws_subnet.private_a.id,
        aws_subnet.private_b.id
    ]
}

// Internet Gateway
output "internet_gateway_id" {
    description = "The ID of the Internet Gateway"
    value       = aws_internet_gateway.igw.id
}

output "internet_gateway_arn" {
    description = "The ARN of the Internet Gateway"
    value       = aws_internet_gateway.igw.arn
}

output "internet_gateway_name" {
    description = "The Name tag of the Internet Gateway"
    value       = aws_internet_gateway.igw.tags["Name"]
}

// Public Route Table
output "public_route_table_id" {
    description = "The ID of the public route table"
    value       = aws_route_table.public_rt.id
}

output "public_route_table_name" {
    description = "The Name tag of the public route table"
    value       = aws_route_table.public_rt.tags["Name"]
}

output "public_route_table_associations" {
    description = "A list of subnet IDs associated with the public route table"
    value = [
        aws_route_table_association.public_rt_a.subnet_id,
        aws_route_table_association.public_rt_b.subnet_id
    ]
}

output "public_route_table_association_ids" {
    description = "A list of IDs for the public route table associations"
    value = [
        aws_route_table_association.public_rt_a.id,
        aws_route_table_association.public_rt_b.id
    ]
}

// Nat Gateway
output "nat_gateway_id" {
    description = "The ID of the NAT Gateway"
    value       = aws_nat_gateway.ngw.id
}

output "nat_gateway_name" {
    description = "The Name tag of the NAT Gateway"
    value       = aws_nat_gateway.ngw.tags["Name"]
}

output "nat_gateway_allocation_id" {
    description = "The Allocation ID of the Elastic IP associated with the NAT Gateway"
    value       = aws_nat_gateway.ngw.allocation_id
}

output "nat_gateway_public_ip" {
    description = "The public IP address of the NAT Gateway"
    value       = aws_nat_gateway.ngw.public_ip
}

output "elastic_ip_id" {
    description = "The ID of the Elastic IP associated with the NAT Gateway"
    value       = aws_eip.ngw_eip.id
}

output "elastic_ip_address" {
    description = "The public IP address of the Elastic IP"
    value       = aws_eip.ngw_eip.public_ip
}

// Private Route Table
output "private_route_table_id" {
    description = "The ID of the private route table"
    value       = aws_route_table.private_rt.id
}

output "private_route_table_name" {
    description = "The Name tag of the private route table"
    value       = aws_route_table.private_rt.tags["Name"]
}

output "private_route_table_associations" {
    description = "A list of subnet IDs associated with the private route table"
    value = [
        aws_route_table_association.private_rt_a.subnet_id,
        aws_route_table_association.private_rt_b.subnet_id
    ]
}

output "private_route_table_association_ids" {
    description = "A list of IDs for the private route table associations"
    value = [
        aws_route_table_association.private_rt_a.id,
        aws_route_table_association.private_rt_b.id
    ]
}
