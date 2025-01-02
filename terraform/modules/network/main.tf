// VPC
resource "aws_vpc" "vpc" {
    cidr_block                       = "10.0.0.0/16"
    assign_generated_ipv6_cidr_block = false
    enable_dns_support               = true
    enable_dns_hostnames             = true
    instance_tenancy                 = "default"
    tags = merge(var.server_tags, {
        Name = "${var.project_name}_vpc"
    })
}

// Public Subnet
resource "aws_subnet" "public_a" {
    vpc_id                          = aws_vpc.vpc.id
    cidr_block                      = "10.0.1.0/24"
    assign_ipv6_address_on_creation = false
    availability_zone               = "${var.region}a"
    map_public_ip_on_launch         = false

    tags = merge(var.server_tags, {
        Name = "${var.project_name}_public_a"
    })
}

resource "aws_subnet" "public_b" {
    vpc_id                          = aws_vpc.vpc.id
    cidr_block                      = "10.0.2.0/24"
    assign_ipv6_address_on_creation = false
    availability_zone               = "${var.region}b"
    map_public_ip_on_launch         = false

    tags = merge(var.server_tags, {
        Name = "${var.project_name}_public_b"
    })
}

resource "aws_subnet" "private_a" {
    vpc_id                          = aws_vpc.vpc.id
    cidr_block                      = "10.0.3.0/24"
    assign_ipv6_address_on_creation = false
    availability_zone               = "${var.region}a"
    map_public_ip_on_launch         = false

    tags = merge(var.server_tags, {
        Name = "${var.project_name}_private_a"
    })
}

resource "aws_subnet" "private_b" {
    vpc_id                          = aws_vpc.vpc.id
    cidr_block                      = "10.0.4.0/24"
    assign_ipv6_address_on_creation = false
    availability_zone               = "${var.region}b"
    map_public_ip_on_launch         = false

    tags = merge(var.server_tags, {
        Name = "${var.project_name}_private_b"
    })
}

resource "aws_internet_gateway" "igw" {
    vpc_id = aws_vpc.vpc.id

    tags = merge(var.gateway_tags, {
        Name = "${var.project_name}_igw"
    })
}

resource "aws_route_table" "public_rt" {
    vpc_id = aws_vpc.vpc.id

    route {
        cidr_block = "0.0.0.0/0"
        gateway_id = aws_internet_gateway.igw.id
    }

    tags = merge(var.server_tags, {
        Name = "${var.project_name}_public_rt"
    })
}

resource "aws_route_table_association" "public_rt_a" {
    subnet_id      = aws_subnet.public_a.id
    route_table_id = aws_route_table.public_rt.id
}

resource "aws_route_table_association" "public_rt_b" {
    subnet_id      = aws_subnet.public_b.id
    route_table_id = aws_route_table.public_rt.id
}

resource "aws_eip" "ngw_eip" {
    tags = merge(var.server_tags, {
        Name = "${var.project_name}_ngw_eip"
    })
}

resource "aws_nat_gateway" "ngw" {
    allocation_id = aws_eip.ngw_eip.id
    subnet_id     = aws_subnet.public_a.id

    tags = merge(var.server_tags, {
        Name = "${var.project_name}_ngw"
    })
}

resource "aws_route_table" "private_rt" {
    vpc_id = aws_vpc.vpc.id

    route {
        cidr_block = "0.0.0.0/0"
        gateway_id = aws_nat_gateway.ngw.id
    }

    tags = merge(var.server_tags, {
        Name = "${var.project_name}_private_rt"
    })
}

resource "aws_route_table_association" "private_rt_a" {
    subnet_id      = aws_subnet.private_a.id
    route_table_id = aws_route_table.private_rt.id
}

resource "aws_route_table_association" "private_rt_b" {
    subnet_id      = aws_subnet.private_b.id
    route_table_id = aws_route_table.private_rt.id
}
