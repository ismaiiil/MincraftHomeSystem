#!/bin/sh
set -e

PAPER_USER='papermc_user'
PAPER_GROUP='papermc_group'

# Create the user and group if not already created
if ! id $PAPER_USER >/dev/null 2>&1; then
    USER_ID=${PUID:-9123}
    GROUP_ID=${PGID:-9123}

    addgroup --gid $GROUP_ID $PAPER_GROUP
    adduser $PAPER_USER --shell /bin/sh --uid $USER_ID --ingroup $PAPER_GROUP --disabled-password --gecos ""

    chown -vR $USER_ID:$GROUP_ID /opt/minecraft
    chmod -vR ug+rwx /opt/minecraft

    if [ "$SKIP_PERM_CHECK" != "true" ]
        then
            chown -vR $USER_ID:$GROUP_ID /data
    fi
fi

export HOME=/home/$PAPER_USER
exec gosu $PAPER_USER:$PAPER_GROUP java $JAVAFLAGS -jar /opt/minecraft/paper.jar nogui
