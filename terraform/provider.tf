provider "aws" {
    region = var.region

    /** Note: access_key와 secret_key는 환경변수를 통해 설정하면 된다.
    export AWS_ACCESS_KEY_ID="your-access-key"
    export AWS_SECRET_ACCESS_KEY="your-secret-key"
     */
}

terraform {
    required_providers {
        aws = {
            source  = "hashicorp/aws"
            version = "5.54.1"
        }
    }
}
