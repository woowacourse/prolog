terraform {
    cloud {
        organization = "cholog"

        workspaces {
            name = "cholog-dev"
        }
    }
}
