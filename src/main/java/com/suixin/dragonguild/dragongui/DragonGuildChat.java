package com.suixin.dragonguild.dragongui;

import com.suixin.dragonguild.DragonGuild;
import com.suixin.dragonguild.entity.DragonGuildChatEntity;
import com.suixin.dragonguild.entity.DragonGuildEntity;
import com.suixin.dragonguild.entity.DragonGuildMemberEntity;
import com.suixin.dragonguild.handler.DragonGuildChatDatabaseHandler;
import com.suixin.dragonguild.handler.DragonGuildDatabaseHandler;
import com.suixin.dragonguild.handler.DragonGuildMemBerDatabaseHandler;
import com.suixin.dragonguild.util.*;
import eos.moe.dragoncore.api.easygui.EasyScreen;
import eos.moe.dragoncore.api.easygui.component.*;
import eos.moe.dragoncore.api.easygui.component.listener.ClickListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;


public class DragonGuildChat {
    //创建GUI
    public static EasyScreen getGui() {
        YamlConfiguration mainGui = DragonGuiYml.getBackground();
        return new EasyScreen(ImageUrlEnum.background.getUrl(), mainGui.getInt("width"), mainGui.getInt("high"));
    }

    //打开GUI
    public static void openGameLobbyGui(Player player,Integer dragonGuildId) {
        EasyScreen gui = createGui(player,dragonGuildId);
        gui.openGui(player);
    }

    //创建组件
    public static EasyScreen createGui(Player player, Integer dragonGuildId) {
        EasyScreen screen = getGui();
        //大厅
        YamlConfiguration lobby = DragonGuiYml.getLobby();
        EasyButton lobbyButton = new EasyButton(lobby.getInt("x"), lobby.getInt("y"), lobby.getInt("width"), lobby.getInt("high"), ImageUrlEnum.lobby.getUrl(), PImageUrlEnum.lobby.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildGui.openGameLobbyGui(player);
            }
        };
        //公告
        YamlConfiguration notice = DragonGuiYml.getNotice();
        EasyButton noticeButton = new EasyButton(notice.getInt("x"), notice.getInt("y"), notice.getInt("width"), notice.getInt("high"), ImageUrlEnum.notice.getUrl(), PImageUrlEnum.notice.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildNotice.openGameLobbyGui(player,dragonGuildId);
            }
        };
        //聊天
        YamlConfiguration chat = DragonGuiYml.getChat();
        EasyButton chatButton = new EasyButton(chat.getInt("x"), chat.getInt("y"), chat.getInt("width"), chat.getInt("high"), PImageUrlEnum.chat.getUrl(), PImageUrlEnum.chat.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
            }
        };
        //审批
        YamlConfiguration apply = DragonGuiYml.getApply();
        EasyButton applyButton = new EasyButton(apply.getInt("x"), apply.getInt("y"), apply.getInt("width"), apply.getInt("high"), ImageUrlEnum.apply.getUrl(), PImageUrlEnum.apply.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildApply.openGameLobbyGui(player,dragonGuildId);
            }
        };
        //排行
        YamlConfiguration top = DragonGuiYml.getTop();
        EasyButton topButton = new EasyButton(top.getInt("x"), top.getInt("y"), top.getInt("width"), top.getInt("high"), ImageUrlEnum.top.getUrl(), PImageUrlEnum.top.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildTop.openGameLobbyGui(player,dragonGuildId);
            }
        };
        //关闭
        YamlConfiguration close = DragonGuiYml.getClose();
        EasyButton closeButton = new EasyButton( close.getInt("x"), close.getInt("y"), close.getInt("width"), close.getInt("high"), ImageUrlEnum.close.getUrl(), PImageUrlEnum.close.getUrl() ) {
            @Override
            public void onClick(Player player, ClickListener.Type type) {
                player.closeInventory();
            }
        };
        //聊天记录
        YamlConfiguration backGroundChatYml = DragonGuiYml.getBackGroundChat();
        EasyScrollingList scrollingList = new EasyScrollingList(backGroundChatYml.getInt("x"), backGroundChatYml.getInt("y"), backGroundChatYml.getInt("width"), backGroundChatYml.getInt("high"), ImageUrlEnum.listbg.getUrl());
        YamlConfiguration barYml = DragonGuiYml.getBar();
        scrollingList.setBar(barYml.getInt("w"), barYml.getInt("h"), barYml.getInt("high")*3, ImageUrlEnum.bar.getUrl());
        Map<String, Component> userComponent = DragonGuildGui.getUserComponent();
        Component component = userComponent.get(player.getName());
        chatList(scrollingList,player, component, 1, dragonGuildId,1);
        component.setScrollingList(scrollingList);
        //聊天内容
        YamlConfiguration shuruContent = DragonGuiYml.getShuruContent();
        final EasyTextField contentTextField = new EasyTextField(shuruContent.getInt("x"), shuruContent.getInt("y"), shuruContent.getInt("width"));

        //发送
        YamlConfiguration send = DragonGuiYml.getSend();
        EasyButton shangyiyeButton = new EasyButton( send.getInt("x"), send.getInt("y"), send.getInt("width"), send.getInt("high"), ImageUrlEnum.send.getUrl(), PImageUrlEnum.send.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildChatEntity dragonGuildChatEntity = new DragonGuildChatEntity();
                dragonGuildChatEntity.setCreator(player.getName());
                dragonGuildChatEntity.setGuildId(dragonGuildId);
                String text = contentTextField.getText();
                dragonGuildChatEntity.setDescs(text);
                dragonGuildChatEntity.setUid(player.getUniqueId().toString());
                dragonGuildChatEntity.setStatus(1);
                dragonGuildChatEntity.setCreated(new Date());
                DragonGuildChatDatabaseHandler.insert(dragonGuildChatEntity);
                List<DragonGuildMemberEntity> dragonGuildMemberEntities = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByDragonGuildId(dragonGuildId);
                for (DragonGuildMemberEntity dragonGuildMemberEntity : dragonGuildMemberEntities) {
                    String uid = dragonGuildMemberEntity.getUid();
                    Player member = Bukkit.getServer().getPlayer(uid);
                    if (member != null) {
                        member.sendMessage(DragonGuild.getSystemConfig().getString("DragonGuild.prefix") +"§e§l" +player.getName() +": §a§l"+text);
                    }
                }
                DragonGuildChat.openGameLobbyGui(player,dragonGuildId);
            }
        };
        YamlConfiguration listBgkYml = DragonGuiYml.getListBgk();
        EasyImage listBgkImg = new EasyImage( listBgkYml.getInt("x"), listBgkYml.getInt("y"), listBgkYml.getInt("width"), listBgkYml.getInt("high"),ImageUrlEnum.listbgk.getUrl());
        YamlConfiguration name = DragonGuiYml.getName();
        DragonGuildEntity dragonGuildEntity = DragonGuildDatabaseHandler.selectDragonGuildById(dragonGuildId);
        EasyLabel nameText = new EasyLabel(name.getInt("x"), name.getInt("y"), 1, Arrays.asList(dragonGuildEntity.getName()));
        Integer count = DragonGuildMemBerDatabaseHandler.selectCount(dragonGuildId);
        YamlConfiguration renshu = DragonGuiYml.getRenshu();
        YamlConfiguration level = DragonGuiYml.getLevel();
        YamlConfiguration systemConfig = DragonGuild.getSystemConfig();

        EasyLabel renshuText = new EasyLabel(renshu.getInt("x"), renshu.getInt("y"), 1, Arrays.asList(systemConfig.getString("DragonGuild.information.member","成员:")+count + "/"+dragonGuildEntity.getMaxMember()));
        EasyLabel levelText = new EasyLabel( level.getInt("x"), level.getInt("y"),1, Arrays.asList(systemConfig.getString("DragonGuild.information.level","等级:")+dragonGuildEntity.getLevel()+""));

        //图标
        YamlConfiguration guildImgYml = DragonGuiYml.getGuildImg();
        EasyImage guildImg = new EasyImage( guildImgYml.getInt("x"), guildImgYml.getInt("y"), guildImgYml.getInt("width"), guildImgYml.getInt("high"),ImageUrlEnum.guildImg.getUrl());
        screen.addComponent(nameText);
        screen.addComponent(renshuText);
        screen.addComponent(levelText);
        screen.addComponent(listBgkImg);
        screen.addComponent(guildImg);
        screen.addComponent(lobbyButton);
        screen.addComponent(noticeButton);
        screen.addComponent(chatButton);
        screen.addComponent(applyButton);
        screen.addComponent(topButton);
        screen.addComponent(scrollingList);
        screen.addComponent(closeButton);
        screen.addComponent(contentTextField);
        screen.addComponent(shangyiyeButton);
        return screen;
    }
    private static void chatList(EasyScrollingList scrollingList,Player player, Component component, Integer limit,Integer dragonGuildId,Integer type) {
        List<EasyComponent> list = new ArrayList<>();
        int currentPage = limit;
        //获取页数
        EasyScreen openedScreen = EasyScreen.getOpenedScreen(player);
        Map<String, EasyComponent> components = new HashMap<>();
        if (openedScreen != null) {
            components = openedScreen.getComponents();
        }
        List<String> chatlist = component.getChatList();
        limit = (limit - 1) * 10;
        YamlConfiguration chatBox = DragonGuiYml.getChatBox();
        YamlConfiguration chatImg = DragonGuiYml.getChatImg();
        YamlConfiguration chatName = DragonGuiYml.getChatName();
        YamlConfiguration chatContent = DragonGuiYml.getChatContent();
        int chatImgY = chatImg.getInt("y");
        int chatBoxY = chatBox.getInt("y");
        int chatNameY = chatName.getInt("y");
        int chatContentY = chatContent.getInt("y");
        List<DragonGuildChatEntity> dragonGuildChatEntities = DragonGuildChatDatabaseHandler.selectDragonGuildDataNum(limit, dragonGuildId);
        if (dragonGuildChatEntities.size() == 0) {
            return;
        }

        for (String easyComponent : chatlist) {
            components.remove(easyComponent);
        }
        chatlist.clear();
        for (DragonGuildChatEntity dragonGuildChatEntity: dragonGuildChatEntities) {
            String creator = dragonGuildChatEntity.getCreator();
            String descs = dragonGuildChatEntity.getDescs();
            DragonGuildEntity dragonGuildEntity = DragonGuildDatabaseHandler.selectDragonGuildByCreator(creator);
            EasyImage img = null;
            if (dragonGuildEntity.getId() == null) {
                img = new EasyImage(chatImg.getInt("x"), chatImgY, chatImg.getInt("width"), chatImg.getInt("high"),ImageUrlEnum.pictureFrame.getUrl());
            }else {
                img = new EasyImage(chatImg.getInt("x"), chatImgY, chatImg.getInt("width"), chatImg.getInt("high"),ImageUrlEnum.creator.getUrl());
            }
            int length = 0;
            try {
                length = descs.getBytes("GBK").length;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            List<String> descList = new ArrayList<>();
            int y = length;
            while (y > 0){
                String s = "";
                try {
                    s = bSubstring(descs, 20);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                descs = descs.replace(s,"");
                y = y - 20;
                descList.add(s);
            }
            
            int high = chatBox.getInt("high");
            int chatBoxY2 = chatBoxY;
            if (descList.size() == 3) {
                high = high + 13;
                chatBoxY2 = chatBoxY2 - 2;
            }else if (descList.size() > 3){
                high = high*2;
                chatBoxY2 = chatBoxY2 - 4;
            }
            EasyImage chatBoxImg = new EasyImage(chatBox.getInt("x"), chatBoxY2, chatBox.getInt("width"), high,ImageUrlEnum.chatBox.getUrl());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            String format = simpleDateFormat.format(dragonGuildChatEntity.getCreated());
            EasyLabel nameText = new EasyLabel(chatName.getInt("x"), chatNameY, 1,Arrays.asList(" §d"+creator+" "+format));
            EasyLabel chatContentText = new EasyLabel(chatContent.getInt("x"), chatContentY, 1,descList);
            list.add(img);
            list.add(chatBoxImg);
            list.add(nameText);
            list.add(chatContentText);
            scrollingList.addComponent(img);
            scrollingList.addComponent(chatBoxImg);
            scrollingList.addComponent(nameText);
            scrollingList.addComponent(chatContentText);
            chatImgY = chatImgY + chatImg.getInt("interval");
            chatBoxY = chatBoxY + chatImg.getInt("interval");
            chatNameY = chatNameY + chatImg.getInt("interval");
            chatContentY = chatContentY + chatImg.getInt("interval");
        }

        component.setCurrent(currentPage);
        for (EasyComponent easyComponent : list) {
            chatlist.add(easyComponent.getId());
        }
        component.setChatList(chatlist);
        Map<String, Component> userComponent = DragonGuildGui.getUserComponent();
        userComponent.put(player.getName(),component);
    }

    public static String bSubstring(String s, int length) throws Exception
    {

        byte[] bytes = s.getBytes("Unicode");
        int n = 0; // 表示当前的字节数
        int i = 2; // 要截取的字节数，从第3个字节开始
        for (; i < bytes.length && n < length; i++)
        {
            // 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
            if (i % 2 == 1)
            {
                n++; // 在UCS2第二个字节时n加1
            }
            else
            {
                // 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
                if (bytes[i] != 0)
                {
                    n++;
                }
            }
        }
        // 如果i为奇数时，处理成偶数
        if (i % 2 == 1)

        {
            // 该UCS2字符是汉字时，去掉这个截一半的汉字
            if (bytes[i - 1] != 0) {
                i = i - 1;
            }
                // 该UCS2字符是字母或数字，则保留该字符
            else {
                i = i + 1;
            }
        }

        return new String(bytes, 0, i, "Unicode");
    }
}