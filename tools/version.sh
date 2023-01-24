#!/bin/bash

set -e

git config --global user.name release-bot
git config --global user.email release-bot

VERSION_NBR=$(cat VERSION)
if [ -z "$VERSION_NBR" ]; then
    VERSION_NBR="0.0.1"
fi

NEW_VERSION=$1
if [ -z "$NEW_VERSION" ]; then
    NEW_VERSION="${VERSION_NBR%.*}.$((${VERSION_NBR##*.} + 1))"
fi

echo "Attempt to release version $NEW_VERSION"

echo $NEW_VERSION >VERSION
git add VERSION
git commit -a -m "Increase version to v$NEW_VERSION."
git push origin $BRANCH_NAME
