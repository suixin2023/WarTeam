package com.suixin.dragonguild.dragongui;

import com.suixin.dragonguild.entity.DragonGuildEntity;
import com.suixin.dragonguild.entity.DragonGuildMemberEntity;
import com.suixin.dragonguild.handler.DragonGuildApplyDatabaseHandler;
import com.suixin.dragonguild.handler.DragonGuildDatabaseHandler;
import com.suixin.dragonguild.handler.DragonGuildMemBerDatabaseHandler;
import com.suixin.dragonguild.util.*;
import eos.moe.dragoncore.api.easygui.EasyScreen;
import eos.moe.dragoncore.api.easygui.component.*;
import eos.moe.dragoncore.api.easygui.component.listener.ClickListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.*;


public class DragonGuildGui {
    public static Map<String, Component> userComponent = new HashMap<>();
    //创建GUI
    public static EasyScreen getGui() {
        YamlConfiguration mainGui = DragonGuiYml.getBackground();
        return new EasyScreen(ImageUrlEnum.background.getUrl(), mainGui.getInt("width"), mainGui.getInt("high"));
    }
    //打开GUI
    public static void openGameLobbyGui(Player player) {
        EasyScreen lhdGui = createLhdGui(player);
        lhdGui.openGui(player);
    }

    //创建组件
    public static EasyScreen createLhdGui(Player player) {
        Component component = new Component();
        userComponent.put(player.getName(),component);
        DragonGuildMemberEntity dragonGuildMemberEntity = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByUid(player.getName());
        Integer id = dragonGuildMemberEntity.getId();
        EasyScreen screen = getGui();
        //大厅
        YamlConfiguration lobby = DragonGuiYml.getLobby();
        EasyButton lobbyButton = new EasyButton(lobby.getInt("x"), lobby.getInt("y"), lobby.getInt("width"), lobby.getInt("high"), PImageUrlEnum.lobby.getUrl(), PImageUrlEnum.lobby.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
            }
        };
        //公告
        YamlConfiguration notice = DragonGuiYml.getNotice();
        EasyButton noticeButton = new EasyButton(notice.getInt("x"), notice.getInt("y"), notice.getInt("width"), notice.getInt("high"), ImageUrlEnum.notice.getUrl(), PImageUrlEnum.notice.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildNotice.openGameLobbyGui(player,id);
            }
        };
        //聊天
        YamlConfiguration chat = DragonGuiYml.getChat();
        EasyButton chatButton = new EasyButton(chat.getInt("x"), chat.getInt("y"), chat.getInt("width"), chat.getInt("high"), ImageUrlEnum.chat.getUrl(), PImageUrlEnum.chat.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildChat.openGameLobbyGui(player,id);
            }
        };
        //审批
        YamlConfiguration apply = DragonGuiYml.getApply();
        EasyButton applyButton = new EasyButton(apply.getInt("x"), apply.getInt("y"), apply.getInt("width"), apply.getInt("high"), ImageUrlEnum.apply.getUrl(), PImageUrlEnum.apply.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildApply.openGameLobbyGui(player,id);
            }
        };
        //排行
        YamlConfiguration top = DragonGuiYml.getTop();
        EasyButton topButton = new EasyButton(top.getInt("x"), top.getInt("y"), top.getInt("width"), top.getInt("high"), ImageUrlEnum.top.getUrl(), PImageUrlEnum.top.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildTop.openGameLobbyGui(player,id);
            }
        };

        EasyLabel nameText = null;
        EasyLabel renshuText = null;
        EasyLabel levelText = null;
        DragonGuildEntity dragonGuildEntity = null;
        if (id != null) {
            dragonGuildEntity = DragonGuildDatabaseHandler.selectDragonGuildById(dragonGuildMemberEntity.getDragonGuildId());
            Integer count = DragonGuildMemBerDatabaseHandler.selectCount(dragonGuildMemberEntity.getDragonGuildId());
            YamlConfiguration name = DragonGuiYml.getName();
            YamlConfiguration renshu = DragonGuiYml.getRenshu();
            YamlConfiguration level = DragonGuiYml.getLevel();
            nameText = new EasyLabel(name.getInt("x"), name.getInt("y"), 1, Arrays.asList(dragonGuildMemberEntity.getDragonGuildName()));
            renshuText = new EasyLabel(renshu.getInt("x"), renshu.getInt("y"), 1, Arrays.asList("成员:"+count + "/"+dragonGuildEntity.getMaxMember()));
            levelText = new EasyLabel( level.getInt("x"), level.getInt("y"),1, Arrays.asList("等级:"+dragonGuildEntity.getLevel()+""));
        }

        //解散公会
        YamlConfiguration dissolveTeam = DragonGuiYml.getDissolveTeam();
        EasyButton dissolveTeamButton = new EasyButton(dissolveTeam.getInt("x"), dissolveTeam.getInt("y"), dissolveTeam.getInt("width"), dissolveTeam.getInt("high"),ImageUrlEnum.dissolveTeam.getUrl(), PImageUrlEnum.dissolveTeam.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildEntity dragonGuildEntity = DragonGuildDatabaseHandler.selectDragonGuildByCreator(player.getName());
                if (dragonGuildEntity.getId() == null) {
                    player.sendMessage(Message.no_team);
                    return;
                }
                DragonGuildDatabaseHandler.deleteById(dragonGuildEntity.getId());
                List<DragonGuildMemberEntity> dragonGuildMemberEntities = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByDragonGuildId(dragonGuildEntity.getId());
                for (DragonGuildMemberEntity dragonGuildMemberEntity : dragonGuildMemberEntities) {
                    String uid = dragonGuildMemberEntity.getUid();
                    Player addressee = Bukkit.getServer().getPlayer(uid);
                    if (addressee != null) {
                        addressee.sendMessage(Message.team_dissolve);
                    }
                }
                DragonGuildMemBerDatabaseHandler.deleteAll(dragonGuildEntity.getId());
                DragonGuildApplyDatabaseHandler.deleteAll(dragonGuildEntity.getId());
                player.closeInventory();
                player.chat("/gh open ");
            }
        };
        //退出公会
        YamlConfiguration outTeam = DragonGuiYml.getOutTeam();
        EasyButton outTeamButton = new EasyButton( outTeam.getInt("x"), outTeam.getInt("y"), outTeam.getInt("width"), outTeam.getInt("high"),ImageUrlEnum.outTeam.getUrl(), PImageUrlEnum.outTeam.getUrl() ) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildEntity dragonGuildEntity = DragonGuildDatabaseHandler.selectDragonGuildByCreator(player.getName());
                if (dragonGuildEntity.getId() != null) {
                    player.sendMessage(Message.not_allow_out_team);
                    return;
                }

                DragonGuildMemberEntity dragonGuildMemberEntity = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByUid(player.getName());
                if (dragonGuildMemberEntity.getId() == null) {
                    player.sendMessage(Message.not_join_oneTeam);
                    return;
                } 
                DragonGuildMemBerDatabaseHandler.deleteById(player.getName());
                player.sendMessage(Message.out_successful);
                player.closeInventory();
                player.chat("/gh open ");
            }
        };
        YamlConfiguration memberBg = DragonGuiYml.getMemberBg();
        EasyScrollingList scrollingList = new EasyScrollingList(memberBg.getInt("x"), memberBg.getInt("y"), memberBg.getInt("width"), memberBg.getInt("high"), ImageUrlEnum.listbg.getUrl());
        YamlConfiguration barYml = DragonGuiYml.getBar();
        scrollingList.setBar(barYml.getInt("w"), barYml.getInt("h"), barYml.getInt("high"), ImageUrlEnum.bar.getUrl());
        memBerList(scrollingList,player, component, 1, dragonGuildMemberEntity,1);
        component.setScrollingList(scrollingList);
        //上一页
        YamlConfiguration shangyiye = DragonGuiYml.getShangyiye();
        EasyButton shangyiyeButton = new EasyButton(shangyiye.getInt("x"), shangyiye.getInt("y"), shangyiye.getInt("width"), shangyiye.getInt("high"),ImageUrlEnum.shangyiye.getUrl(), PImageUrlEnum.shangyiye.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                Component component = DragonGuildGui.getUserComponent().get(player.getName());
                Integer currentPage = component.getCurrent();
                if (currentPage == 1) {
                    return;
                }
                EasyScreen openedScreen = EasyScreen.getOpenedScreen(player);
                EasyScrollingList scrollingList1 = component.getScrollingList();
                memBerList(scrollingList1,player, component, currentPage - 1, dragonGuildMemberEntity, 2);
                openedScreen.addComponent(scrollingList1);
                userComponent.put(player.getName(),component);
                openedScreen.updateGui(player);
            }
        };

        //下一页
        YamlConfiguration xiayiye = DragonGuiYml.getXiayiye();
        EasyButton xiayiyeButton = new EasyButton(xiayiye.getInt("x"), xiayiye.getInt("y"), xiayiye.getInt("width"), xiayiye.getInt("high"),ImageUrlEnum.xiayiye.getUrl(), PImageUrlEnum.xiayiye.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                Component component = DragonGuildGui.getUserComponent().get(player.getName());
                Integer limit = component.getCurrent() + 1;
                EasyScreen openedScreen = EasyScreen.getOpenedScreen(player);
                EasyScrollingList scrollingList1 = component.getScrollingList();
                memBerList(scrollingList1,player, component, limit, dragonGuildMemberEntity, 2);
                openedScreen.addComponent(scrollingList1);
                userComponent.put(player.getName(),component);
                openedScreen.updateGui(player);
            }
        };

        //修改公会名
        YamlConfiguration updateName = DragonGuiYml.getUpdateName();
        EasyButton updateNameButton = new EasyButton( updateName.getInt("x"), updateName.getInt("y"), updateName.getInt("width"), updateName.getInt("high"),ImageUrlEnum.updateName.getUrl(), PImageUrlEnum.updateName.getUrl() ) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildOut.openGameLobbyGui(player,1,dragonGuildMemberEntity.getDragonGuildId());
            }
        };

        //踢出公会
        YamlConfiguration kickOut = DragonGuiYml.getKickOut();
        EasyButton kickOutButton = new EasyButton(kickOut.getInt("x"), kickOut.getInt("y"), kickOut.getInt("width"), kickOut.getInt("high"), ImageUrlEnum.kickOut.getUrl(), PImageUrlEnum.kickOut.getUrl()) {
            @Override
            public void onClick(Player player, ClickListener.Type type) {
                DragonGuildOut.openGameLobbyGui(player,2,dragonGuildMemberEntity.getDragonGuildId());
            }
        };
        //图标
        YamlConfiguration guildImgYml = DragonGuiYml.getGuildImg();
        EasyImage guildImg = new EasyImage( guildImgYml.getInt("x"), guildImgYml.getInt("y"), guildImgYml.getInt("width"), guildImgYml.getInt("high"),ImageUrlEnum.guildImg.getUrl());

        YamlConfiguration listBgkYml = DragonGuiYml.getListBgk();
        EasyImage listBgkImg = new EasyImage( listBgkYml.getInt("x"), listBgkYml.getInt("y"), listBgkYml.getInt("width"), listBgkYml.getInt("high"),ImageUrlEnum.listbgk.getUrl());
        //审批数量
        YamlConfiguration applyNum = DragonGuiYml.getApplyNum();
        Integer integer = DragonGuildApplyDatabaseHandler.selectCount(dragonGuildMemberEntity.getDragonGuildId());
        EasyLabel applyNumText = new EasyLabel(applyNum.getInt("x"), applyNum.getInt("y"), 1, Arrays.asList(integer.toString()));
        screen.addComponent(listBgkImg);
        if (dragonGuildMemberEntity.getUid().equals(dragonGuildEntity.getCreator())) {
            screen.addComponent(updateNameButton);
            screen.addComponent(kickOutButton);
            screen.addComponent(dissolveTeamButton);
            screen.addComponent("审批数量",applyNumText);
        }else {
            screen.addComponent(outTeamButton);
        }
        screen.addComponent(shangyiyeButton);
        screen.addComponent(xiayiyeButton);
        screen.addComponent("名字",nameText);
        screen.addComponent("人数",renshuText);
        screen.addComponent("等级",levelText);
        screen.addComponent(lobbyButton);
        screen.addComponent(noticeButton);
        screen.addComponent(chatButton);
        screen.addComponent(applyButton);
        screen.addComponent(topButton);
        screen.addComponent(guildImg);
        screen.addComponent(scrollingList);
        userComponent.put(player.getName(),component);
        return screen;
    }

    private static void memBerList(EasyScrollingList scrollingList,Player player, Component component, Integer limit, DragonGuildMemberEntity dragonGuildMemberEntity, Integer type) {
        List<EasyComponent> list = new ArrayList<>();
        int currentPage = limit;
        //获取页数
        Map<String, EasyComponent> components = new HashMap<>();
        EasyScreen openedScreen = EasyScreen.getOpenedScreen(player);
        if(openedScreen != null) {
            components = openedScreen.getComponents();
        }
        List<String> memBerlist = component.getMemBerList();
        limit = (limit - 1) * 14;
        YamlConfiguration memberList = DragonGuiYml.getMemberList();
        YamlConfiguration pictureFrameYml = DragonGuiYml.getPictureFrame();
        YamlConfiguration nameYml = DragonGuiYml.getNickName();
        YamlConfiguration expYml = DragonGuiYml.getExp();
        int memberListx = memberList.getInt("x");
        int pictureFramedefx = pictureFrameYml.getInt("x");
        int namex = nameYml.getInt("x");
        int expx = expYml.getInt("x");

        int memberListY = memberList.getInt("y");
        int pictureFramey = pictureFrameYml.getInt("y");
        int namey = nameYml.getInt("y");
        int expy = expYml.getInt("y");

        List<DragonGuildMemberEntity> dragonGuildMemberEntities = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerDataNum(limit, dragonGuildMemberEntity.getDragonGuildId());
        DragonGuildEntity dragonGuildEntity = DragonGuildDatabaseHandler.selectDragonGuildById(dragonGuildMemberEntity.getDragonGuildId());
        if (dragonGuildMemberEntities.size() == 0) {
            player.sendMessage(Message.no_more_member);
            return;
        }
        for (String memBerId : memBerlist) {
            components.remove(memBerId);
        }
        memBerlist.clear();
        for (DragonGuildMemberEntity dragonGuildMember: dragonGuildMemberEntities) {
            String playerName = dragonGuildMember.getUid();
            Integer exp = dragonGuildMember.getExp();
            EasyImage listImage = new EasyImage( memberListx, memberListY, memberList.getInt("width"), memberList.getInt("high"),ImageUrlEnum.memberList.getUrl());
            EasyImage pictureFrameImage = null;
            if (dragonGuildMember.getUid().equals(dragonGuildEntity.getCreator())) {
                pictureFrameImage = new EasyImage( pictureFramedefx, pictureFramey, pictureFrameYml.getInt("width"), pictureFrameYml.getInt("high"),ImageUrlEnum.creator.getUrl());
            }else {
                pictureFrameImage = new EasyImage(pictureFramedefx, pictureFramey, pictureFrameYml.getInt("width"), pictureFrameYml.getInt("high"),ImageUrlEnum.pictureFrame.getUrl());
            }
            EasyLabel nameText = new EasyLabel(namex, namey, 1, Arrays.asList("游戏名："+playerName));
            EasyLabel expText = new EasyLabel(expx, expy, 1 ,Arrays.asList("贡献："+exp));
            list.add(listImage);
            list.add(pictureFrameImage);
            list.add(nameText);
            list.add(expText);
            scrollingList.addComponent(listImage);
            scrollingList.addComponent(pictureFrameImage);
            scrollingList.addComponent(nameText);
            scrollingList.addComponent(nameText);
            memberListY = memberListY + memberList.getInt("interval");
            pictureFramey = pictureFramey + memberList.getInt("interval");
            namey = namey + memberList.getInt("interval");
            expy = expy + memberList.getInt("interval");
        }

        component.setCurrent(currentPage);
        for (EasyComponent easyComponent : list) {
            memBerlist.add(easyComponent.getId());
        }
        component.setMemBerList(memBerlist);
        userComponent.put(player.getName(),component);
    }

    public static Map<String, Component> getUserComponent() {
        return userComponent;
    }

    public static void setUserComponent(Map<String, Component> userComponent) {
        DragonGuildGui.userComponent = userComponent;
    }
}