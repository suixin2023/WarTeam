package com.suixin.warteam.gui;

import com.suixin.warteam.util.ImageUrlEnum;
import com.suixin.warteam.util.VvGuiYml;
import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.ButtonFunction;
import lk.vexview.gui.components.VexButton;
import lk.vexview.gui.components.expand.VexColorfulTextField;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;



public class WarTeamWindow {
    //创建GUI
    public static VexGui getGui() {
        YamlConfiguration window = VvGuiYml.getWindow();
        VexGui vexGui = new VexGui(ImageUrlEnum.window.getUrl(), window.getInt("x"), window.getInt("y"), window.getInt("width"), window.getInt("high"));
        return vexGui;
    }

    //打开GUI
    public static void openGameLobbyGui(Player p, Integer type) {
        VexViewAPI.openGui(p, createGui(p,type));
    }

    //创建VV组件
    public static VexGui createGui(Player player,Integer type) {
        VexGui gui = getGui();
        YamlConfiguration shurukuang = VvGuiYml.getShurukuang();
        final VexColorfulTextField shurukuangTextField = new VexColorfulTextField(shurukuang.getInt("x"), shurukuang.getInt("y"), shurukuang.getInt("width"), shurukuang.getInt("high"),11,1,0x00FF3366,0x00663366,"请输入");
        //确定键
        YamlConfiguration confirm = VvGuiYml.getConfirm();
        VexButton confirmButton = new VexButton("confirmButton", "", ImageUrlEnum.confirm.getUrl(), ImageUrlEnum.confirm.getUrl(), confirm.getInt("x"), confirm.getInt("y"), confirm.getInt("width"), confirm.getInt("high"), new ButtonFunction() {
            @Override
            public void run(Player player) {
                String typedText = shurukuangTextField.getTypedText();
                if (type == 1){
                    player.chat("/wt create "+typedText);
                }else {
                    player.chat("/wt join "+typedText);
                }
                WarTeamGui.openGameLobbyGui(player);
            }
        });

        gui.addComponent(shurukuangTextField);
        gui.addComponent(confirmButton);
        return gui;
    }
}