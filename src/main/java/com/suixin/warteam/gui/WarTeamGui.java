package com.suixin.warteam.gui;

import com.suixin.warteam.entity.WarTeamEntity;
import com.suixin.warteam.entity.WarTeamMemberEntity;
import com.suixin.warteam.handler.WarTeamApplyDatabaseHandler;
import com.suixin.warteam.handler.WarTeamDatabaseHandler;
import com.suixin.warteam.handler.WarTeamMemBerDatabaseHandler;
import com.suixin.warteam.util.*;
import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.OpenedVexGui;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.*;


public class WarTeamGui {
    private static Map<String, Component> userComponent = new HashMap<>();
    //创建GUI
    public static VexGui getGui() {
        YamlConfiguration mainGui = VvGuiYml.getBackground();
        VexGui vexGui = new VexGui(ImageUrlEnum.background.getUrl(), mainGui.getInt("x"), mainGui.getInt("y"), mainGui.getInt("width"), mainGui.getInt("high"));
        return vexGui;
    }
    //打开GUI
    public static void openGameLobbyGui(Player p) {
        VexViewAPI.openGui(p, createLhdGui(p));
    }

    //创建VV组件
    public static VexGui createLhdGui(Player player) {
        Component component = new Component();
        userComponent.put(player.getName(),component);
        WarTeamMemberEntity warTeamMemberEntity = WarTeamMemBerDatabaseHandler.selectWarTeamMemBerByUid(player.getName());
        Integer id = warTeamMemberEntity.getId();
        VexGui gui = getGui();
        //创建战队
        YamlConfiguration create = VvGuiYml.getCreate();
        VexButton createButton = new VexButton("createButton", "", ImageUrlEnum.create.getUrl(), PImageUrlEnum.create.getUrl(), create.getInt("x"), create.getInt("y"), create.getInt("width"), create.getInt("high"), new ButtonFunction() {
            @Override
            public void run(Player player) {
                WarTeamWindow.openGameLobbyGui(player,1);
            }
        });
        VexText nameText = null;
        VexText renshuText = null;
        VexText levelText = null;
        WarTeamEntity warTeamEntity = null;
        if (id != null) {
            warTeamEntity = WarTeamDatabaseHandler.selectWarTeamById(warTeamMemberEntity.getWarTeamId());
            Integer count = WarTeamMemBerDatabaseHandler.selectCount(warTeamMemberEntity.getWarTeamId());
            YamlConfiguration name = VvGuiYml.getName();
            YamlConfiguration renshu = VvGuiYml.getRenshu();
            YamlConfiguration level = VvGuiYml.getLevel();
            nameText = new VexText( name.getInt("x"), name.getInt("y"), Arrays.asList(warTeamMemberEntity.getWarTeamName()),1.2);
            renshuText = new VexText( renshu.getInt("x"), renshu.getInt("y"), Arrays.asList(count + "/"+warTeamEntity.getMaxMember()),1.2);
            levelText = new VexText( level.getInt("x"), level.getInt("y"), Arrays.asList(warTeamEntity.getLevel()+""),1.2);
        }
        //加入战队
        YamlConfiguration join = VvGuiYml.getJoin();
        VexButton joinButton = new VexButton("joinButton", "", ImageUrlEnum.join.getUrl(), PImageUrlEnum.join.getUrl(), join.getInt("x"), join.getInt("y"), join.getInt("width"), join.getInt("high"), new ButtonFunction() {
            @Override
            public void run(Player player) {
                WarTeamWindow.openGameLobbyGui(player,2);
            }
        });
        //解散战队
        YamlConfiguration dissolveTeam = VvGuiYml.getDissolveTeam();
        VexButton dissolveTeamButton = new VexButton("dissolveTeamButton", "", ImageUrlEnum.dissolveTeam.getUrl(), PImageUrlEnum.dissolveTeam.getUrl(), dissolveTeam.getInt("x"), dissolveTeam.getInt("y"), dissolveTeam.getInt("width"), dissolveTeam.getInt("high"), new ButtonFunction() {
            @Override
            public void run(Player player) {
                WarTeamEntity warTeamEntity = WarTeamDatabaseHandler.selectWarTeamByCreator(player.getName());
                if (warTeamEntity.getId() == null) {
                    player.sendMessage(Message.no_team);
                    return;
                }
                WarTeamDatabaseHandler.deleteById(warTeamEntity.getId());
                List<WarTeamMemberEntity> warTeamMemberEntities = WarTeamMemBerDatabaseHandler.selectWarTeamMemBerByWarTeamId(warTeamEntity.getId());
                for (WarTeamMemberEntity warTeamMemberEntity : warTeamMemberEntities) {
                    String uid = warTeamMemberEntity.getUid();
                    Player addressee = Bukkit.getServer().getPlayer(uid);
                    if (addressee != null) {
                        addressee.sendMessage(Message.team_dissolve);
                    }
                }
                WarTeamMemBerDatabaseHandler.deleteAll(warTeamEntity.getId());
                WarTeamApplyDatabaseHandler.deleteAll(warTeamEntity.getId());
                player.closeInventory();
                player.chat("/wt open ");
            }
        });
        //退出战队
        YamlConfiguration outTeam = VvGuiYml.getOutTeam();
        VexButton outTeamButton = new VexButton("outTeamButton", "", ImageUrlEnum.outTeam.getUrl(), PImageUrlEnum.outTeam.getUrl(), outTeam.getInt("x"), outTeam.getInt("y"), outTeam.getInt("width"), outTeam.getInt("high"), new ButtonFunction() {
            @Override
            public void run(Player player) {
                WarTeamEntity warTeamEntity = WarTeamDatabaseHandler.selectWarTeamByCreator(player.getName());
                if (warTeamEntity.getId() != null) {
                    player.sendMessage(Message.not_allow_out_team);
                    return;
                }

                WarTeamMemberEntity warTeamMemberEntity = WarTeamMemBerDatabaseHandler.selectWarTeamMemBerByUid(player.getName());
                if (warTeamMemberEntity.getId() == null) {
                    player.sendMessage(Message.not_join_oneTeam);
                    return;
                }
                WarTeamMemBerDatabaseHandler.deleteById(player.getName());
                player.sendMessage(Message.out_successful);
                player.closeInventory();
                player.chat("/wt open ");
            }
        });
        List<VexComponents> vexComponents = new ArrayList<>();
        if (id != null) {
            vexComponents = memBerList(player, component, 1, warTeamMemberEntity,1);
        }
        //上一页
        YamlConfiguration shangyiye = VvGuiYml.getShangyiye();
        VexButton shangyiyeButton = new VexButton("shangyiyeButton", "", ImageUrlEnum.shangyiye.getUrl(), PImageUrlEnum.shangyiye.getUrl(), shangyiye.getInt("x"), shangyiye.getInt("y"), shangyiye.getInt("width"), shangyiye.getInt("high"), new ButtonFunction() {
            @Override
            public void run(Player player) {
                Component component = WarTeamGui.getUserComponent().get(player.getName());
                Integer currentPage = component.getCurrent();
                if (currentPage == 1) {
                    return;
                }
                memBerList(player,component,currentPage - 1,warTeamMemberEntity,2);
            }
        });

        //下一页
        YamlConfiguration xiayiye = VvGuiYml.getXiayiye();
        VexButton xiayiyeButton = new VexButton("xiayiyeButton", "", ImageUrlEnum.xiayiye.getUrl(), PImageUrlEnum.xiayiye.getUrl(), xiayiye.getInt("x"), xiayiye.getInt("y"), xiayiye.getInt("width"), xiayiye.getInt("high"), new ButtonFunction() {
            @Override
            public void run(Player player) {
                Component component = WarTeamGui.getUserComponent().get(player.getName());
                Integer limit = component.getCurrent() + 1;
                memBerList(player,component,limit,warTeamMemberEntity,2);
            }
        });

        //修改战队名
        YamlConfiguration updateName = VvGuiYml.getUpdateName();
        VexButton updateNameButton = new VexButton("updateNameButton", "", ImageUrlEnum.updateName.getUrl(), PImageUrlEnum.updateName.getUrl(), updateName.getInt("x"), updateName.getInt("y"), updateName.getInt("width"), updateName.getInt("high"), new ButtonFunction() {
            @Override
            public void run(Player player) {
                WarTeamWindow.openGameLobbyGui(player,3);
            }
        });

        //踢出战队
        YamlConfiguration kickOut = VvGuiYml.getKickOut();
        VexButton kickOutButton = new VexButton("kickOutButton", "", ImageUrlEnum.kickOut.getUrl(), PImageUrlEnum.kickOut.getUrl(), kickOut.getInt("x"), kickOut.getInt("y"), kickOut.getInt("width"), kickOut.getInt("high"), new ButtonFunction() {
            @Override
            public void run(Player player) {
                WarTeamWindow.openGameLobbyGui(player,4);
            }
        });

        YamlConfiguration backgroundOfNoTeam = VvGuiYml.getBackgroundOfNoTeam();
        VexImage backgroundOfNoTeamImage = new VexImage(ImageUrlEnum.backgroundOfNoTeam.getUrl(),  backgroundOfNoTeam.getInt("x"), backgroundOfNoTeam.getInt("y"), backgroundOfNoTeam.getInt("width"), backgroundOfNoTeam.getInt("high"));

        if (id != null) {
            if (warTeamMemberEntity.getUid().equals(warTeamEntity.getCreator())) {
                gui.addComponent(updateNameButton);
                gui.addComponent(kickOutButton);
                gui.addComponent(dissolveTeamButton);
            }else {
                gui.addComponent(outTeamButton);
            }
            gui.addComponent(shangyiyeButton);
            gui.addComponent(xiayiyeButton);
            gui.addComponent(nameText);
            gui.addComponent(renshuText);
            gui.addComponent(levelText);
            if (vexComponents.size()> 0) {
                for (VexComponents vexComponent : vexComponents) {
                    gui.addComponent(vexComponent);
                }
            }
        }else {
            gui.addComponent(createButton);
            gui.addComponent(joinButton);
            gui.addComponent(backgroundOfNoTeamImage);
        }
        return gui;
    }

    private static List<VexComponents> memBerList(Player player, Component component, Integer limit,WarTeamMemberEntity warTeamMemberEntity,Integer type) {
        List<VexComponents> list = new ArrayList<>();
        int currentPage = limit;
        //获取页数
        OpenedVexGui opg = VexViewAPI.getPlayerCurrentGui(player);
        List<DynamicComponent> memBerlist = component.getMemBerlist();
        limit = (limit - 1) * 14;
        YamlConfiguration pictureFrameYml = VvGuiYml.getPictureFrame();
        YamlConfiguration nameYml = VvGuiYml.getNickName();
        YamlConfiguration expYml = VvGuiYml.getExp();
        int pictureFramedefx = pictureFrameYml.getInt("x");
        int namedefx = nameYml.getInt("x");
        int expdefx = expYml.getInt("x");

        int pictureFramex = pictureFrameYml.getInt("x");
        int namex = nameYml.getInt("x");
        int expx = expYml.getInt("x");

        int pictureFramey = pictureFrameYml.getInt("y");
        int namey = nameYml.getInt("y");
        int expy = expYml.getInt("y");

        List<WarTeamMemberEntity> warTeamMemberEntities = WarTeamMemBerDatabaseHandler.selectWarTeamMemBerDataNum(limit, warTeamMemberEntity.getWarTeamId());
        WarTeamEntity warTeamEntity = WarTeamDatabaseHandler.selectWarTeamById(warTeamMemberEntity.getWarTeamId());
        if (warTeamMemberEntities.size() == 0) {
            player.sendMessage(Message.no_more_member);
            return list;
        }
        for (DynamicComponent dynamicComponent : memBerlist) {
            opg.removeDynamicComponent(dynamicComponent);
        }
        int i = 1;
        for (WarTeamMemberEntity warTeamMember: warTeamMemberEntities) {
            String playerName = warTeamMember.getUid();
            Integer exp = warTeamMember.getExp();
            VexImage pictureFrameImage = null;
            if (warTeamMember.getUid().equals(warTeamEntity.getCreator())) {
                pictureFrameImage = new VexImage(ImageUrlEnum.creator.getUrl(), pictureFramex, pictureFramey, pictureFrameYml.getInt("width"), pictureFrameYml.getInt("high"));
            }else {
                pictureFrameImage = new VexImage(ImageUrlEnum.pictureFrame.getUrl(), pictureFramex, pictureFramey, pictureFrameYml.getInt("width"), pictureFrameYml.getInt("high"));
            }
            VexText nameText = new VexText(namex, namey, Arrays.asList("游戏名："+playerName),1.0);
            VexText expText = new VexText(expx, expy, Arrays.asList("贡献："+exp),1.0);
            if (type == 1) {
                list.add(pictureFrameImage);
                list.add(nameText);
                list.add(expText);
                memBerlist.add(pictureFrameImage);
                memBerlist.add(nameText);
                memBerlist.add(expText);
            }else {
                opg.addDynamicComponent(pictureFrameImage);
                opg.addDynamicComponent(nameText);
                opg.addDynamicComponent(expText);
                memBerlist.add(pictureFrameImage);
                memBerlist.add(nameText);
                memBerlist.add(expText);
            }
           if (i == 7) {
                pictureFramex = pictureFramedefx;
                namex = namedefx;
                expx = expdefx;
                pictureFramey = pictureFramey + pictureFrameYml.getInt("y") + pictureFrameYml.getInt("yinterval");
                namey = namey + pictureFrameYml.getInt("y")+ pictureFrameYml.getInt("yinterval");
                expy = expy + pictureFrameYml.getInt("y")+ pictureFrameYml.getInt("yinterval");
            }else {
                pictureFramex = pictureFramex + pictureFrameYml.getInt("x") + pictureFrameYml.getInt("xinterval");
                namex = namex + pictureFrameYml.getInt("x")+ pictureFrameYml.getInt("xinterval");
                expx = expx + pictureFrameYml.getInt("x")+ pictureFrameYml.getInt("xinterval");
            }
            i++;
        }

        component.setCurrent(currentPage);
        component.setMemBerlist(memBerlist);
        userComponent.put(player.getName(),component);
        return list;
    }

    public static Map<String, Component> getUserComponent() {
        return userComponent;
    }

    public static void setUserComponent(Map<String, Component> userComponent) {
        WarTeamGui.userComponent = userComponent;
    }
}