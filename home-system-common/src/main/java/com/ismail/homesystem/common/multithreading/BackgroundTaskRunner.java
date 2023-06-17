package com.ismail.homesystem.common.multithreading;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class BackgroundTaskRunner extends BukkitRunnable{
    private final Runnable task;
    private final Runnable callback;

    public BackgroundTaskRunner(Runnable task, Runnable callback) {
        this.task = task;
        this.callback = callback;
    }

    @Override
    public void run() {
        // Code to be executed on a background thread
        task.run();

        // Run the callback on the main thread
        Bukkit.getScheduler().runTask((Plugin) this, callback);
    }
}
