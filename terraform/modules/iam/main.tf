resource "aws_iam_role" "ec2_role" {
    name = "${var.project_name}-s3-access-role"

    assume_role_policy = jsonencode({
        Version = "2012-10-17",
        Statement = [
            {
                Action = "sts:AssumeRole",
                Effect = "Allow",
                Principal = {
                    Service = "ec2.amazonaws.com"
                }
            }
        ]
    })
}

resource "aws_iam_policy" "s3_access_policy" {
    name = "${var.project_name}-s3-access-policy"

    policy = jsonencode({
        Version = "2012-10-17",
        Statement = [
            {
                Action = [
                    "s3:Get*",
                    "s3:List*"
                ],
                Effect   = "Allow",
                Resource = var.bucket_arns
            }
        ]
    })

    // Note: Tag not required
}

resource "aws_iam_role_policy_attachment" "s3_access_attachment" {
    role       = aws_iam_role.ec2_role.name
    policy_arn = aws_iam_policy.s3_access_policy.arn
}

resource "aws_iam_policy" "secrets_manager_access_policy" {
    name = "${var.project_name}-secrets_manager_access-policy"

    policy = jsonencode({
        Version = "2012-10-17",
        Statement = [
            {
                Action = [
                    "secretsmanager:GetSecretValue"
                ],
                Effect   = "Allow",
                Resource = "*"
            }
        ]
    })

    // Note: Tag not required
}

resource "aws_iam_role_policy_attachment" "secrets_manager_access_attachment" {
    role       = aws_iam_role.ec2_role.name
    policy_arn = aws_iam_policy.secrets_manager_access_policy.arn
}

resource "aws_iam_role" "code_deploy_role" {
    name = "${var.project_name}-code-deploy-role"

    assume_role_policy = jsonencode({
        Version = "2012-10-17",
        Statement = [
            {
                Action = "sts:AssumeRole",
                Effect = "Allow",
                Principal = {
                    Service = "codedeploy.amazonaws.com"
                }
            }
        ]
    })
}

resource "aws_iam_policy" "code_deploy_policy" {
    name = "${var.project_name}-code-deploy-policy"

    policy = jsonencode({
        Version = "2012-10-17",
        Statement = [
            {
                Action = [
                    "autoscaling:*",
                    "ec2:*",
                    "elasticloadbalancing:*",
                    "cloudwatch:PutMetricData",
                    "s3:Get*",
                    "s3:List*"
                ],
                Effect   = "Allow",
                Resource = "*"
            }

        ]
    })

    // Note: Tag not required
}

resource "aws_iam_role_policy_attachment" "code_deploy_attachment" {
    role       = aws_iam_role.code_deploy_role.name
    policy_arn = aws_iam_policy.code_deploy_policy.arn
}
