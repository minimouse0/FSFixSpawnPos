package com.minimouse48.fsfixspawnpos;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.*;

public class PlayerJoinEventHandler implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws IOException {
        Player player=event.getPlayer();
        FileConfiguration conf=FSFixSpawnPos.Plugin.getConfig();
        //计算参考坐标，为了能玩家到世界重生点的水平距离，必须有一个提供抵消掉Y轴的坐标
        Location spawnPointCenter=player.getWorld().getSpawnLocation();
        //把计算参考坐标的Y轴设置为与玩家的相同，确保计算时计算的是水平距离
        spawnPointCenter.setY(player.getLocation().getY());
        //如果玩家坐标到计算参考坐标（这样计算才能抵消水平距离）小于配置文件中的spawnpoint-distance（距离世界重生点的水平距离）
        if(player.getLocation().distance(spawnPointCenter)<conf.getDouble("spawnpoint-distance")){
            //传送玩家到配置文件中指定的坐标
            player.teleport(new Location(player.getWorld(),conf.getDouble("x"),conf.getDouble("y"),conf.getDouble("z")));

        }
        //String path=System.getProperty("user.dir")+File.separator+"plugins"+File.separator+"FSFixSpawnPos"+File.separator+"config.yml";
        //
        //event.getPlayer().sendMessage(event.getPlayer().getWorld().getSpawnLocation().toString());
        //player.sendMessage(String.valueOf(YamlConfiguration.loadConfiguration(new File(path)).getInt("qwer")));
        //player.sendMessage(path);
        /*
        String line;
        FileInputStream fis=new FileInputStream(new File(path));
        BufferedReader br=new BufferedReader(new InputStreamReader(fis));
        int i;
        for(i=1;(line=br.readLine())!=null&&i<=20;i++){
            player.sendMessage(line);
        }
        br.close();
        fis.close();

         */
        /*
        YamlConfiguration conf=YamlConfiguration.loadConfiguration(new File(path));
        if(player.getLocation().distance(player.getWorld().getSpawnLocation())<conf.getDouble("qwer")){
            player.sendMessage("离出生点");
            player.teleport(new Location(player.getWorld(),conf.getDouble("x"),conf.getDouble("y"),conf.getDouble("z")));
        }

         */
    }

}
