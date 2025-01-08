resource "aws_s3_bucket" "bucket" {
    bucket        = var.bucket_name
    force_destroy = true

    tags = merge(var.storage_tags, {
        Name = "${var.project_name}_bucket"
    })
}

resource "aws_s3_bucket_public_access_block" "public_access" {
    bucket = aws_s3_bucket.bucket.id

    block_public_acls       = true
    block_public_policy     = true
    ignore_public_acls      = true
    restrict_public_buckets = true
}
