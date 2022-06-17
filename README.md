# findy-common-kt

[![test](https://github.com/findy-network/findy-common-kt/actions/workflows/test.yml/badge.svg)](https://github.com/findy-network/findy-common-kt/actions/workflows/test.yml)

## Setup env

1. Setup gradle

    ```bash
    ./tools/setup-gradle.sh
    ```

2. Run tests

    ```bash
    ./gradlew check
    ```

3. Check licenses

    ```bash
    ./gradlew checkLicense
    ```

4. Run sample

    1. [Install findy-agent-cli](https://github.com/findy-network/findy-agent-cli#installation)
    1. [Setup local agency](https://github.com/findy-network/findy-wallet-pwa/tree/dev/tools/env#agency-setup-for-local-development)
    or use cloud installation.
    If using cloud installation, [define needed environment variables](./sample/src/main/kotlin/org/findy_network/findy_common_kt/sample/Sample.kt#L14).
    By default the sample tries to connect with local installation.
    1. Run app

        ```bash
        ./gradlew :sample:Sample
        ```

    1. Sample will print connection invitation.
    When another aries agent connects with the invitation,
    sample sends greeting through basic message protocol.

## Publishing

1. Publish libraries to local maven repository

    ```bash
    ./gradlew publishToMavenLocal
    ```

1. Publish libraries to GitHub
   1. Define following env variables:

        ```bash
        export USERNAME=<your_gh_username>
        export TOKEN=<your_gh_token_with_packages_access>
        ```

   1. Increase version number in `build.gradle.kts`
   1. Publish to GitHub:

        ```bash
        ./gradlew publish
        ```
