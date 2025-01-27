terraform {
    cloud {
        organization = "cholog"

        workspaces {
            name = "cholog-prod"
        }
    }
}
