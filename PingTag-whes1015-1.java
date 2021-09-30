package com.pingtag;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerLoginEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.LoginChainData;
import cn.nukkit.utils.TextFormat;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class whes1015 extends PluginBase implements Listener {

    private static whes1015 plugin;
    int vercode=101;
    String vername="V 1.0.1";

    @Override
    public void onEnable() {

        URL url = null;
        try {
            url = new URL("http://220.134.162.44:81/whes1015pingtag.php");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection http = null;
        try {
            assert url != null;
            http = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert http != null;
            http.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        try {
            InputStream input = http.getInputStream();
            byte[] data = new byte[1024];
            int idx = input.read(data);
            String str = new String(data, 0, idx);
            int x = Integer.parseInt(str);
            if(vercode < x) {
                this.getLogger().warning(TextFormat.RED + "Please Update Your Plugin! "+vername);
                this.getServer().shutdown();
            }else{
                this.getLogger().info(TextFormat.BLUE + "PingTag whes1015 Update Checking Success! "+vername);

            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        http.disconnect();

        this.getLogger().info(TextFormat.BLUE + "PingTag whes1015 Loading! "+vername);
        this.getServer().getPluginManager().registerEvents(this, this);

    }

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent event) {
        String ping=event.getPlayer().getPing() + "(ms)";
        String OS;
        LoginChainData os=event.getPlayer().getLoginChainData();
        if(os.getDeviceOS() == 1){

             OS="Android";
        }else if(os.getDeviceOS() == 2){

             OS="iOS";
        }else{

             OS="Windows";
        }
        if (event.getPlayer().getPing()>300) {
            event.getPlayer().setNameTag(event.getPlayer().getName() + "\n" +TextFormat.BLUE +OS+" "+ TextFormat.RED +ping);
        }else if(event.getPlayer().getPing()>100) {
            event.getPlayer().setNameTag(event.getPlayer().getName() + "\n" +TextFormat.BLUE +OS+" "+ TextFormat.YELLOW +ping);
        }else{

                event.getPlayer().setNameTag(event.getPlayer().getName() + "\n" +TextFormat.BLUE +OS+" "+ TextFormat.GREEN +ping);
            }
        }
    }

