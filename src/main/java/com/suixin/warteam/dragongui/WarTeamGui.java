package com.suixin.warteam.dragongui;

import com.suixin.warteam.entity.WarTeamEntity;
import com.suixin.warteam.entity.WarTeamMemberEntity;
import com.suixin.warteam.handler.WarTeamApplyDatabaseHandler;
import com.suixin.warteam.handler.WarTeamDatabaseHandler;
import com.suixin.warteam.handler.WarTeamMemBerDatabaseHandler;
import com.suixin.warteam.util.*;
import eos.moe.dragoncore.api.easygui.EasyScreen;
import eos.moe.dragoncore.api.easygui.component.*;
import eos.moe.dragoncore.api.easygui.component.listener.ClickListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.*;


public class WarTeamGui {
    private static Map<String, Component> userComponent = new HashMap<>();
    //创建GUI
    public static EasyScreen getGui() {
        YamlConfiguration mainGui = VvGuiYml.getBackground();
        return new EasyScreen(ImageUrlEnum.background.getUrl(), mainGui.getInt("width"), mainGui.getInt("high"));
    }
    //打开GUI
    public static void openGameLobbyGui(Player player) {
        EasyScreen lhdGui = createLhdGui(player);
        lhdGui.openGui(player);
    }

    //创建VV组件
    public static EasyScreen createLhdGui(Player player) {
        Component component = new Component();
        userComponent.put(player.getName(),component);
        WarTeamMemberEntity warTeamMemberEntity = WarTeamMemBerDatabaseHandler.selectWarTeamMemBerByUid(player.getName());
        Integer id = warTeamMemberEntity.getId();
        EasyScreen screen = getGui();
        //创建战队
        YamlConfiguration create = VvGuiYml.getCreate();
        EasyButton createButton = new EasyButton(create.getInt("x"), create.getInt("y"), create.getInt("width"), create.getInt("high"), ImageUrlEnum.create.getUrl(), PImageUrlEnum.create.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                EasyScreen openedScreen = EasyScreen.getOpenedScreen(player);
                openedScreen.onClose(player);
                WarTeamWindow.openGameLobbyGui(player, 1);
            }
        };
        EasyLabel nameText = null;
        EasyLabel renshuText = null;
        EasyLabel levelText = null;
        WarTeamEntity warTeamEntity = null;
        if (id != null) {
            warTeamEntity = WarTeamDatabaseHandler.selectWarTeamById(warTeamMemberEntity.getWarTeamId());
            Integer count = WarTeamMemBerDatabaseHandler.selectCount(warTeamMemberEntity.getWarTeamId());
            YamlConfiguration name = VvGuiYml.getName();
            YamlConfiguration renshu = VvGuiYml.getRenshu();
            YamlConfiguration level = VvGuiYml.getLevel();
            nameText = new EasyLabel(name.getInt("x"), name.getInt("y"), 1, Arrays.asList(warTeamMemberEntity.getWarTeamName()));
            renshuText = new EasyLabel(renshu.getInt("x"), renshu.getInt("y"), 1, Arrays.asList(count + "/"+warTeamEntity.getMaxMember()));
            levelText = new EasyLabel( level.getInt("x"), level.getInt("y"),1, Arrays.asList(warTeamEntity.getLevel()+""));
        }
        //加入战队
        YamlConfiguration join = VvGuiYml.getJoin();
        EasyButton joinButton = new EasyButton(join.getInt("x"), join.getInt("y"), join.getInt("width"), join.getInt("high"),ImageUrlEnum.join.getUrl(), PImageUrlEnum.join.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                EasyScreen openedScreen = EasyScreen.getOpenedScreen(player);
                openedScreen.onClose(player);
                WarTeamWindow.openGameLobbyGui(player,2);
            }
        };
        //解散战队
        YamlConfiguration dissolveTeam = VvGuiYml.getDissolveTeam();
        EasyButton dissolveTeamButton = new EasyButton(dissolveTeam.getInt("x"), dissolveTeam.getInt("y"), dissolveTeam.getInt("width"), dissolveTeam.getInt("high"),ImageUrlEnum.dissolveTeam.getUrl(), PImageUrlEnum.dissolveTeam.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
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
        };
        //退出战队
        YamlConfiguration outTeam = VvGuiYml.getOutTeam();
        EasyButton outTeamButton = new EasyButton( outTeam.getInt("x"), outTeam.getInt("y"), outTeam.getInt("width"), outTeam.getInt("high"),ImageUrlEnum.outTeam.getUrl(), PImageUrlEnum.outTeam.getUrl() ) {
            @Override
            public void onClick(Player player, Type type) {
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
        };
        List<EasyComponent> easyComponents = new ArrayList<>();
        if (id != null) {
            easyComponents = memBerList(player, component, 1, warTeamMemberEntity,1);
        }
        //上一页
        YamlConfiguration shangyiye = VvGuiYml.getShangyiye();
        EasyButton shangyiyeButton = new EasyButton(shangyiye.getInt("x"), shangyiye.getInt("y"), shangyiye.getInt("width"), shangyiye.getInt("high"),ImageUrlEnum.shangyiye.getUrl(), PImageUrlEnum.shangyiye.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                Component component = WarTeamGui.getUserComponent().get(player.getName());
                Integer currentPage = component.getCurrent();
                if (currentPage == 1) {
                    return;
                }
                memBerList(player,component,currentPage - 1,warTeamMemberEntity,2);
            }
        };

        //下一页
        YamlConfiguration xiayiye = VvGuiYml.getXiayiye();
        EasyButton xiayiyeButton = new EasyButton(xiayiye.getInt("x"), xiayiye.getInt("y"), xiayiye.getInt("width"), xiayiye.getInt("high"),ImageUrlEnum.xiayiye.getUrl(), PImageUrlEnum.xiayiye.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                Component component = WarTeamGui.getUserComponent().get(player.getName());
                Integer limit = component.getCurrent() + 1;
                memBerList(player,component,limit,warTeamMemberEntity,2);
            }
        };

        //修改战队名
        YamlConfiguration updateName = VvGuiYml.getUpdateName();
        EasyButton updateNameButton = new EasyButton( updateName.getInt("x"), updateName.getInt("y"), updateName.getInt("width"), updateName.getInt("high"),ImageUrlEnum.updateName.getUrl(), PImageUrlEnum.updateName.getUrl() ) {
            @Override
            public void onClick(Player player, Type type) {
                WarTeamWindow.openGameLobbyGui(player,3);
            }
        };

        //踢出战队
        YamlConfiguration kickOut = VvGuiYml.getKickOut();
        EasyButton kickOutButton = new EasyButton(kickOut.getInt("x"), kickOut.getInt("y"), kickOut.getInt("width"), kickOut.getInt("high"), ImageUrlEnum.kickOut.getUrl(), PImageUrlEnum.kickOut.getUrl()) {
            @Override
            public void onClick(Player player, ClickListener.Type type) {
                WarTeamWindow.openGameLobbyGui(player,4);
            }
        };

        //审批列表
        YamlConfiguration applyList = VvGuiYml.getApplyButton();
        EasyButton applyListButton = new EasyButton(applyList.getInt("x"), applyList.getInt("y"), applyList.getInt("width"), applyList.getInt("high"),ImageUrlEnum.applyList.getUrl(), PImageUrlEnum.applyList.getUrl() ) {
            @Override
            public void onClick(Player player, ClickListener.Type type) {
                EasyScreen openedScreen = EasyScreen.getOpenedScreen(player);
                openedScreen.onClose(player);
                WarTeamApply.openGameLobbyGui(player,warTeamMemberEntity.getWarTeamId());
            }
        };
        //审批数量
        YamlConfiguration applyNum = VvGuiYml.getApplyNum();
        Integer integer = WarTeamApplyDatabaseHandler.selectCount(warTeamMemberEntity.getWarTeamId());
        EasyLabel applyNumText = new EasyLabel(applyNum.getInt("x"), applyNum.getInt("y"), 1, Arrays.asList(integer.toString()));
        YamlConfiguration backgroundOfNoTeam = VvGuiYml.getBackgroundOfNoTeam();
        EasyImage backgroundOfNoTeamImage = new EasyImage( backgroundOfNoTeam.getInt("x"), backgroundOfNoTeam.getInt("y"), backgroundOfNoTeam.getInt("width"), backgroundOfNoTeam.getInt("high"),ImageUrlEnum.backgroundOfNoTeam.getUrl());

        if (id != null) {
            if (warTeamMemberEntity.getUid().equals(warTeamEntity.getCreator())) {
                screen.addComponent(updateNameButton);
                screen.addComponent(kickOutButton);
                screen.addComponent(dissolveTeamButton);
                screen.addComponent(applyListButton);
                screen.addComponent("审批数量",applyNumText);
            }else {
                screen.addComponent(outTeamButton);
            }
            screen.addComponent(shangyiyeButton);
            screen.addComponent(xiayiyeButton);
            screen.addComponent("名字",nameText);
            screen.addComponent("人数",renshuText);
            screen.addComponent("等级",levelText);
            if (easyComponents.size()> 0) {
                for (EasyComponent easyComponent : easyComponents) {
                    screen.addComponent(easyComponent);
                }
            }
        }else {
            screen.addComponent(createButton);
            screen.addComponent(joinButton);
            screen.addComponent(backgroundOfNoTeamImage);
        }
        return screen;
    }

    private static List<EasyComponent> memBerList(Player player, Component component, Integer limit,WarTeamMemberEntity warTeamMemberEntity,Integer type) {
        List<EasyComponent> list = new ArrayList<>();
        int currentPage = limit;
        //获取页数
        EasyScreen openedScreen = EasyScreen.getOpenedScreen(player);
        Map<String, EasyComponent> components = openedScreen.getComponents();
        List<EasyComponent> memBerlist = component.getMemBerlist();
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
        for (EasyComponent dynamicComponent : memBerlist) {
            components.remove(dynamicComponent);
        }
        int i = 1;
        for (WarTeamMemberEntity warTeamMember: warTeamMemberEntities) {
            String playerName = warTeamMember.getUid();
            Integer exp = warTeamMember.getExp();
            EasyImage pictureFrameImage = null;
            if (warTeamMember.getUid().equals(warTeamEntity.getCreator())) {
                pictureFrameImage = new EasyImage( pictureFramex, pictureFramey, pictureFrameYml.getInt("width"), pictureFrameYml.getInt("high"),ImageUrlEnum.creator.getUrl());
            }else {
                pictureFrameImage = new EasyImage(pictureFramex, pictureFramey, pictureFrameYml.getInt("width"), pictureFrameYml.getInt("high"),ImageUrlEnum.pictureFrame.getUrl());
            }
            EasyLabel nameText = new EasyLabel(namex, namey, 1, Arrays.asList("游戏名："+playerName));
            EasyLabel expText = new EasyLabel(expx, expy, 1 ,Arrays.asList("贡献："+exp));
            if (type == 1) {
                list.add(pictureFrameImage);
                list.add(nameText);
                list.add(expText);
                memBerlist.add(pictureFrameImage);
                memBerlist.add(nameText);
                memBerlist.add(expText);
            }else {
                components.put("pictureFrameImage",pictureFrameImage);
                components.put("nameText",nameText);
                components.put("expText",expText);
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
        openedScreen.updateGui(player);
        return list;
    }

    public static Map<String, Component> getUserComponent() {
        return userComponent;
    }

    public static void setUserComponent(Map<String, Component> userComponent) {
        WarTeamGui.userComponent = userComponent;
    }
}