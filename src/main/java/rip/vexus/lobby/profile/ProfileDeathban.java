package rip.vexus.lobby.profile;

import lombok.Getter;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import rip.vexus.lobby.utils.DateUtil;

public class ProfileDeathban {


    @Getter private final long createdAt;
    @Getter private final long duration;

    public ProfileDeathban(long createdAt, long duration) {
        this.createdAt = createdAt;
        this.duration = duration;
    }

    public ProfileDeathban(long duration) {
        this(System.currentTimeMillis(), duration * 1000);
    }

    public String getTimeLeft() {
        return DateUtil.readableTime((createdAt + duration) - System.currentTimeMillis()).trim();
    }

    public static int getDuration(Player player) {
        int duration = 0;

        for (PermissionAttachmentInfo info : player.getEffectivePermissions()) {
            String perm = info.getPermission();

            if (perm.startsWith("deathban.")) {
                int tempDuration = 0;

                try {
                    tempDuration = Integer.parseInt(perm.replace("deathban.", "").replace(" ", ""));
                } catch (NumberFormatException ignored) {}

                if (duration > 0 && tempDuration > duration) {
                    continue;
                }

                duration = tempDuration;
            }
        }

        return duration;
    }

}
