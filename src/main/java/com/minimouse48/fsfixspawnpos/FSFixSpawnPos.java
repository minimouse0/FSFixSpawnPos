package com.minimouse48.fsfixspawnpos;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Server;

import javax.annotation.Nullable;
import java.io.File;
import java.util.List;

public final class FSFixSpawnPos extends JavaPlugin {
    /**
     * 后文如果要获得插件本体
     * 需要从这个作为入口的类获取
     * 虽然JavaPlugin有getPlugin这个方法
     * 但是getPlugin必须提供插件信息才能获取到
     * 那还不如直接存到这个入口里
     * 需要的时候都来这个入口获取就行
     * 反正插件本体也不会变
     */
    public static Plugin Plugin;
    @Override
    public void onEnable() {
        // Plugin startup logic
        //初始化获取插件实例本体的属性Plugin
        Plugin = FSFixSpawnPos.getProvidingPlugin(FSFixSpawnPos.class);
        //注册事件
        Bukkit.getPluginManager().registerEvents(new PlayerJoinEventHandler(),this);
        loadConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * 加载配置文件
     */
    private void loadConfig(){
        boolean config_gen=false;
        /*
        虽然saveDefaultConfig已经会在文件不存在的时候自动写入
        但是还要判断一下config.yml是否存在的原因是
        因为配置文件里面有一些配置项是需要根据世界的实际情况初始化的
        这个paper平台不像llse的JsonConfigFile
        直接JsonConfigFile.init()就可以根据游戏的实际情况初始化键值
        它需要把config.yml里面的东西复制到那里面
        而config.yml是死的
        我就得在确定插件是自动生成了配置之后，马上去修改里面的内容
         */
        if(!new File(this.getDataFolder().getPath()+File.separator+"config.yml").exists()){
            //写入默认配置文件
            saveDefaultConfig();
            //修改默认的传送目标(0,0,0)为配置文件生成时的世界出生点
            FileConfiguration conf=getConfig();
            @Nullable Location worldSpawn=null;
            List<World> worlds=Bukkit.getWorlds();
            for(World currentWorld:worlds) {
                if(currentWorld.getName().equals("world")){
                    worldSpawn=currentWorld.getSpawnLocation();
                    break;
                }
            }
            conf.set("x",worldSpawn.getX());
            conf.set("z",worldSpawn.getZ());
            this.saveConfig();
        }
        String conf_path=System.getProperty("user.dir")+File.separator+"plugins"+File.separator+"FSFixSpawnPos"+File.separator+"config.yml";
        YamlConfiguration conf=YamlConfiguration.loadConfiguration(new File(conf_path));
    }

}
