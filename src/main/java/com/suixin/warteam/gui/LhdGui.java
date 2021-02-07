package com.suixin.warteam.gui;

import com.suixin.tavern.Tavern;
import com.suixin.tavern.dao.impl.JettonImpl;
import com.suixin.tavern.entity.*;
import com.suixin.tavern.enums.ImageUrlEnum;
import com.suixin.tavern.enums.PitchImageUrlEnum;
import com.suixin.tavern.handler.BetDatabaseHandler;
import com.suixin.tavern.handler.ConfigDatabaseHandler;
import com.suixin.tavern.handler.HistoryLotteryHandler;
import com.suixin.tavern.util.Message;
import com.suixin.tavern.util.MessageVv;
import com.suixin.tavern.util.VvGuiYml;
import com.suixin.tavern.vexview.entity.VexButtonEx;
import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.OpenedVexGui;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.*;
import lk.vexview.gui.components.expand.VexColorfulTextField;
import lk.vexview.gui.components.expand.VexGifImage;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.suixin.tavern.command.BetCommand.selectLhdTime;

public class LhdGui {
    //创建GUI
    public static VexGui getGui(){
        YamlConfiguration mainGui = VvGuiYml.getBaseGui();
        VexGui vexGui = new VexGui(ImageUrlEnum.LHD_BACKGROUND.getUrl(), mainGui.getInt("x"), mainGui.getInt("y"), mainGui.getInt("width"), mainGui.getInt("high"));
        return vexGui;
    }
    //打开GUI
    public static void openGameLobbyGui(Player p){
        VexViewAPI.openGui(p,createLhdGui(p));
    }
    //创建VV组件
    public static VexGui createLhdGui(Player player){
        Component component = new Component();
        component.setPageType(2);
        VexGui gui = getGui();
        //图片路径,local代表本地，X轴，Y轴，宽度，高度
        //萌之争开奖图片
        //动态图
        YamlConfiguration lhdDynamicImage = VvGuiYml.getLhdDynamicImage();
        VexGifImage dynamicImage = new VexGifImage(ImageUrlEnum.LHD_DYNAMIC.getUrl(), lhdDynamicImage.getInt("x"), lhdDynamicImage.getInt("y"), lhdDynamicImage.getInt("width"), lhdDynamicImage.getInt("high"),lhdDynamicImage.getInt("xshow"),lhdDynamicImage.getInt("yshow"),0);
        //结果背景
        YamlConfiguration lhdResultbackground = VvGuiYml.getLhdResultbackground();
        VexImage resultbackgroundImage = new VexImage(ImageUrlEnum.LHD_RESULTBACKGROUND.getUrl(), lhdResultbackground.getInt("x"), lhdResultbackground.getInt("y"), lhdResultbackground.getInt("width"), lhdResultbackground.getInt("high"));
        component.setLhdResultbackgroundImage(resultbackgroundImage);
        //查询最近一期猜猜看开奖记录
        List<HistoryLotteryEntity> historyLotteryEntities = HistoryLotteryHandler.selectHistoryLotteryByType("萌之争", 1);
        VexText lotteryInformation = null;
        if (historyLotteryEntities.size() > 0) {
            HistoryLotteryEntity historyLotteryEntity = historyLotteryEntities.get(0);
            YamlConfiguration lhdMessage = VvGuiYml.getLhdMessage();
            lotteryInformation = new VexText(lhdMessage.getInt("x"), lhdMessage.getInt("y"), Arrays.asList("§a第§6"+historyLotteryEntity.getPeriods()+"§a期【萌之争】结果：§6"+historyLotteryEntity.getResult()),1.2);
            component.setLhdLotteryInformation(lotteryInformation);
        }

        //id，内容，未选中图片，选中图片，X轴，Y轴，宽度，高度
        //历史记录
        YamlConfiguration lhdHistory = VvGuiYml.getLhdHistory();
        VexButtonEx lhdHistoryButton = new VexButtonEx("lhdHistoryButton", "", ImageUrlEnum.LHD_HISTORY.getUrl(), PitchImageUrlEnum.LHD_HISTORY.getUrl(), lhdHistory.getInt("x"), lhdHistory.getInt("y"), lhdHistory.getInt("width"), lhdHistory.getInt("high"),new ButtonFunction(){
            @Override
            public void run(Player player) {
                //打开历史开奖记录
                Component componentMap = MainGui.getUserComponent().get(player.getUniqueId().toString());
                OpenedVexGui opg = VexViewAPI.getPlayerCurrentGui(player);
                Integer open = componentMap.getOpen();
                if (open == 1) {
                    //关闭历史记录
                    VexImage lhdHistoryListImage = componentMap.getLhdHistoryListImage();
                    VexText lhdHistoryListText = componentMap.getLhdHistoryListText();
                    opg.removeDynamicComponent(lhdHistoryListImage);
                    opg.removeDynamicComponent(lhdHistoryListText);
                    componentMap.setOpen(0);
                    MainGui.getUserComponent().put(player.getUniqueId().toString(),componentMap);
                    return;
                }
                //打开历史开奖记录
                YamlConfiguration lhdHistoryList = VvGuiYml.getLhdHistoryList();
                VexImage lhdHistoryListImage = new VexImage(ImageUrlEnum.LHD_HISTORY_LIST.getUrl(), lhdHistoryList.getInt("x"), lhdHistoryList.getInt("y"), lhdHistoryList.getInt("width"), lhdHistoryList.getInt("high"));
                //查询近13期开奖记录
                List<HistoryLotteryEntity> lotteryEntities = HistoryLotteryHandler.selectHistoryLotteryByType("萌之争", 13);
                List<String> lotteryList = componentMap.getLotteryList();
                lotteryList.clear();
                for (HistoryLotteryEntity lotteryEntity : lotteryEntities) {
                    String periods = lotteryEntity.getPeriods();
                    String result = lotteryEntity.getResult();
                    lotteryList.add("§2第"+periods+"期："+result);
                }
                YamlConfiguration lhdHistoryListTextConfig = VvGuiYml.getLhdHistoryListText();
                VexText lhdHistoryListText = new VexText(lhdHistoryListTextConfig.getInt("x"), lhdHistoryListTextConfig.getInt("y"),lotteryList,1);

                opg.addDynamicComponent(lhdHistoryListImage);
                opg.addDynamicComponent(lhdHistoryListText);
                componentMap.setLhdHistoryListImage(lhdHistoryListImage);
                componentMap.setLhdHistoryListText(lhdHistoryListText);
                componentMap.setOpen(1);
                MainGui.getUserComponent().put(player.getUniqueId().toString(),componentMap);
            }
        });

        //刷新开奖时间时间
//        YamlConfiguration refresh = VvGuiYml.getLhdRefresh();
        YamlConfiguration refreshTime = VvGuiYml.getLhdRefreshTime();
        //查询本期猜猜看的开奖时间
        StringBuffer time = selectLhdTime();
        List<String> refreshTimeMessage = component.getRefreshTimeMessage();
        refreshTimeMessage.clear();
        if (time == null) {
            refreshTimeMessage.add("§a奖励发放中，请稍等片刻！");
        } else {
            refreshTimeMessage.add("§a开奖倒计时还有§e"+time);
        }
        VexText refreshTimeText = new VexText(refreshTime.getInt("x"), refreshTime.getInt("y"),refreshTimeMessage,1.0);
        component.setLhdRefreshTimeText(refreshTimeText);
//        VexButtonEx refreshButtosn = new VexButtonEx(498446, "", ImageUrlEnum.MAIN_REFRESH.getUrl(), PitchImageUrlEnum.MAIN_REFRESH.getUrl(),refresh.getInt("x"),refresh.getInt("y"), refresh.getInt("width"), refresh.getInt("high"),new ButtonFunction(){
//            @Override
//            public void run(Player player) {
//                OpenedVexGui opg = VexViewAPI.getPlayerCurrentGui(player);
//                Component componentMap = MainGui.getUserComponent().get(player.getUniqueId().toString());
//                VexText refreshTimeText = componentMap.getLhdRefreshTimeText();
//                opg.removeDynamicComponent(refreshTimeText);
//                //查询本期猜猜看的开奖时间
//                StringBuffer time = selectLhdTime();
//                refreshTimeMessage.clear();
//                if (time == null) {
//                    refreshTimeMessage.add("§a奖励发放中，请稍等片刻！");
//                } else {
//                    refreshTimeMessage.add("§a开奖倒计时还有§e"+time);
//                }
//                refreshTimeText = new VexText(refreshTime.getInt("x"), refreshTime.getInt("y"),refreshTimeMessage,1.0);
//                opg.addDynamicComponent(refreshTimeText);
//                componentMap.setLhdRefreshTimeText(refreshTimeText);
//                MainGui.getUserComponent().put(player.getUniqueId().toString(),componentMap);
//            }
//        });
        //押注银豆输入文本
        //x轴, y轴, 宽, 高, 最大字数, id
        YamlConfiguration lhdshurukuang = VvGuiYml.getLhdshurukuang();
        VexImage lhdShurukuangImage = new VexImage(ImageUrlEnum.LHD_SHURUKUANG.getUrl(), lhdshurukuang.getInt("x"), lhdshurukuang.getInt("y"), lhdshurukuang.getInt("width"), lhdshurukuang.getInt("high"));
        final VexColorfulTextField sum = new VexColorfulTextField(lhdshurukuang.getInt("x")+10, lhdshurukuang.getInt("y"), lhdshurukuang.getInt("width"), lhdshurukuang.getInt("high"),11,1,0x00FF3366,0x00663366,"100");

        YamlConfiguration lhdLong = VvGuiYml.getLhdLong();
        VexButtonEx singleButton = new VexButtonEx("singleButton", "", ImageUrlEnum.LHD_DRAGON.getUrl(), PitchImageUrlEnum.LHD_DRAGON.getUrl(), lhdLong.getInt("x"),lhdLong.getInt("y"), lhdLong.getInt("width"), lhdLong.getInt("high"),new ButtonFunction(){
            @Override
            public void run(Player player) {
                //押注龙"
                Component componentMap = MainGui.getUserComponent().get(player.getUniqueId().toString());
                Integer disable = componentMap.getDisable();
                if (disable == 1) {
                    return;
                }
                lhdBetOn(player,"龙",sum);
            }
        });
        YamlConfiguration lhdHu = VvGuiYml.getLhdHu();
        VexButtonEx doubleButton = new VexButtonEx("doubleButton", "", ImageUrlEnum.LHD_TIGER.getUrl(), PitchImageUrlEnum.LHD_TIGER.getUrl(), lhdHu.getInt("x"),lhdHu.getInt("y"), lhdHu.getInt("width"), lhdHu.getInt("high"),new ButtonFunction(){
            @Override
            public void run(Player player) {
                //押注虎
                Component componentMap = MainGui.getUserComponent().get(player.getUniqueId().toString());
                Integer disable = componentMap.getDisable();
                if (disable == 1) {
                    return;
                }
                lhdBetOn(player,"虎",sum);
            }
        });
        YamlConfiguration lhdHe = VvGuiYml.getLhdHe();
        VexButtonEx leopardButton = new VexButtonEx("leopardButton", "", ImageUrlEnum.LHD_DRAW.getUrl(), PitchImageUrlEnum.LHD_DRAW.getUrl(), lhdHe.getInt("x"),lhdHe.getInt("y"), lhdHe.getInt("width"), lhdHe.getInt("high"),new ButtonFunction(){
            @Override
            public void run(Player player) {
                //押注和
                Component componentMap = MainGui.getUserComponent().get(player.getUniqueId().toString());
                Integer disable = componentMap.getDisable();
                if (disable == 1) {
                    return;
                }
                lhdBetOn(player,"和",sum);
            }
        });

        YamlConfiguration mainClose = VvGuiYml.getBaseclose();
        VexButtonEx closeButton = new VexButtonEx(ImageUrlEnum.CLOSE.getId(), "", ImageUrlEnum.CLOSE.getUrl(), PitchImageUrlEnum.CLOSE.getUrl(),mainClose.getInt("x"),mainClose.getInt("y"), mainClose.getInt("width"), mainClose.getInt("high"),new ButtonFunction(){
            @Override
            public void run(Player player) {
                //关闭当前界面
                player.closeInventory();
            }
        });
        YamlConfiguration mainBack = VvGuiYml.getBaseBack();
        VexButtonEx back = new VexButtonEx("back", "", ImageUrlEnum.BACK.getUrl(), PitchImageUrlEnum.BACK.getUrl(), mainBack.getInt("x"),mainBack.getInt("y"), mainBack.getInt("width"), mainBack.getInt("high"),new ButtonFunction(){
            @Override
            public void run(Player player) {
                //返回
                MainGui.openGameLobbyGui(player);
            }
        });

        //押注金额
        //押注选项
        List<String> message = new ArrayList<>();
        message.add("§e押注金额:");
        List<String> message2 = new ArrayList<>();
        message2.add("§e押注选项:");
        YamlConfiguration lhdMoney = VvGuiYml.getLhdMoney();
        YamlConfiguration lhdChose = VvGuiYml.getLhdChose();
        VexText vexText = new VexText(lhdMoney.getInt("x"), lhdMoney.getInt("y"),message,2.0);
        VexText vexText2 = new VexText(lhdChose.getInt("x"), lhdChose.getInt("y"),message2,2.0);

        //金豆银豆
        YamlConfiguration base_jindou = VvGuiYml.getBase_jindou();
        YamlConfiguration base_jindounum = VvGuiYml.getBase_jindounum();
        YamlConfiguration base_yindou = VvGuiYml.getBase_yindou();
        YamlConfiguration base_yindounum = VvGuiYml.getBase_yindounum();

        VexImage baseJindouImage = new VexImage(ImageUrlEnum.BASE_JINDOU.getUrl(), base_jindou.getInt("x"), base_jindou.getInt("y"), base_jindou.getInt("width"), base_jindou.getInt("high"));
        VexImage baseYindouImage = new VexImage(ImageUrlEnum.BASE_YINDOU.getUrl(), base_yindou.getInt("x"), base_yindou.getInt("y"), base_yindou.getInt("width"), base_yindou.getInt("high"));
        //查询账户信息
        JettonImpl jettonImpl = JettonImpl.getJettonImpl();
        JettonEntity jettonEntity = jettonImpl.selectJettonById(player.getUniqueId().toString());
        List<String> jindouMessage = component.getJindouMessage();
        List<String> yindouMessage = component.getYindouMessage();
        jindouMessage.clear();
        yindouMessage.clear();
        jindouMessage.add("§e金豆: §6"+jettonEntity.getJettonB());
        yindouMessage.add("§3银豆: §6"+jettonEntity.getJettonA());
        VexText jindouText = new VexText(base_jindounum.getInt("x"), base_jindounum.getInt("y"),jindouMessage,1.0);
        VexText yindouText = new VexText(base_yindounum.getInt("x"), base_yindounum.getInt("y"),yindouMessage,1.0);
        component.setJindouText(jindouText);
        component.setYindouText(yindouText);
        MainGui.getUserComponent().put(player.getUniqueId().toString(),component);

        gui.addComponent(resultbackgroundImage);
        gui.addComponent(dynamicImage);
        if (lotteryInformation != null) {
            gui.addComponent(lotteryInformation);
        }
        gui.addComponent(lhdHistoryButton);
//        gui.addComponent(refreshButtosn);
        gui.addComponent(lhdShurukuangImage);
        gui.addComponent(refreshTimeText);
        gui.addComponent(jindouText);
        gui.addComponent(yindouText);
        gui.addComponent(sum);
        gui.addComponent(singleButton);
        gui.addComponent(doubleButton);
        gui.addComponent(leopardButton);
        gui.addComponent(vexText);
        gui.addComponent(vexText2);
        gui.addComponent(baseJindouImage);
        gui.addComponent(baseYindouImage);
        gui.addComponent(back);
        gui.addComponent(closeButton);
        return gui;
    }

    private static void bet(Player player, String betType, Integer amount, JettonImpl jettonImpl, BigDecimal jettonA){
        Map<String, Component> userComponent = MainGui.getUserComponent();
        Component componentMap = userComponent.get(player.getUniqueId().toString());
        //押注提示框
        YamlConfiguration lhdBetKuang = VvGuiYml.getLhdBetKuang();
        VexImage lhdBetkuangImage = new VexImage(ImageUrlEnum.LHD_BETKUANG.getUrl(), lhdBetKuang.getInt("x"), lhdBetKuang.getInt("y"), lhdBetKuang.getInt("width"), lhdBetKuang.getInt("high"));
        //押注提示语句
        YamlConfiguration lhdBetMessage = VvGuiYml.getLhdBetMessage();
        List<String> betMessages = componentMap.getBetMessages();
        VexText lhdBetMessageTest = new VexText(lhdBetMessage.getInt("x"), lhdBetMessage.getInt("y"),betMessages,1.2);
        //确认按钮
        YamlConfiguration lhdConfirm = VvGuiYml.getLhdBetConfirm();
        VexButtonEx lhdBetConfirmButton = new VexButtonEx("lhdBetConfirmButton", "", ImageUrlEnum.LHD_CONFIRM.getUrl(), PitchImageUrlEnum.LHD_CONFIRM.getUrl(), lhdConfirm.getInt("x"),lhdConfirm.getInt("y"), lhdConfirm.getInt("width"), lhdConfirm.getInt("high"),new ButtonFunction(){
            @Override
            public void run(Player player) {
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS,1,1);
                //确认
                betOn(player,amount,jettonImpl, betType, jettonA);
                //关闭当前选框
                Component componentMap = MainGui.getUserComponent().get(player.getUniqueId().toString());
                VexImage lhdBetkuangImage1 = componentMap.getLhdBetkuangImage();
                VexText lhdBetMessageTest = componentMap.getLhdBetMessageTest();
                VexButtonEx lhdBetConfirmButton = componentMap.getLhdBetConfirmButton();
                VexButtonEx lhdCancelButton = componentMap.getLhdCancelButton();
                componentMap.setDisable(0);
                MainGui.getUserComponent().put(player.getUniqueId().toString(),componentMap);
                OpenedVexGui opg = VexViewAPI.getPlayerCurrentGui(player);
                opg.removeDynamicComponent(lhdBetkuangImage1);
                opg.removeDynamicComponent(lhdBetMessageTest);
                opg.removeDynamicComponent(lhdBetConfirmButton);
                opg.removeDynamicComponent(lhdCancelButton);
                opg.addDynamicComponent(componentMap.getLhdLotteryInformation());
                refreshMoney(player);
            }
        });
        //取消按钮
        YamlConfiguration lhdCancel = VvGuiYml.getLhdCancel();
        VexButtonEx lhdCancelButton = new VexButtonEx("lhdCancelButton", "", ImageUrlEnum.LHD_CANCEL.getUrl(), PitchImageUrlEnum.LHD_CANCEL.getUrl(), lhdCancel.getInt("x"),lhdCancel.getInt("y"), lhdCancel.getInt("width"), lhdCancel.getInt("high"),new ButtonFunction(){
            @Override
            public void run(Player player) {
                //关闭当前选框
                Component componentMap = MainGui.getUserComponent().get(player.getUniqueId().toString());
                VexImage lhdBetkuangImage1 = componentMap.getLhdBetkuangImage();
                VexText lhdBetMessageTest = componentMap.getLhdBetMessageTest();
                VexButtonEx lhdBetConfirmButton = componentMap.getLhdBetConfirmButton();
                VexButtonEx lhdCancelButton = componentMap.getLhdCancelButton();
                OpenedVexGui opg = VexViewAPI.getPlayerCurrentGui(player);
                componentMap.setDisable(0);
                MainGui.getUserComponent().put(player.getUniqueId().toString(),componentMap);
                opg.removeDynamicComponent(lhdBetkuangImage1);
                opg.removeDynamicComponent(lhdBetMessageTest);
                opg.removeDynamicComponent(lhdBetConfirmButton);
                opg.removeDynamicComponent(lhdCancelButton);
                opg.addDynamicComponent(componentMap.getLhdLotteryInformation());
            }
        });
        OpenedVexGui opg = VexViewAPI.getPlayerCurrentGui(player);
        VexGui vexGui = opg.getVexGui();
        List<VexComponents> beforComponents = new ArrayList<>();
        beforComponents.add(lhdBetkuangImage);
        beforComponents.add(lhdBetMessageTest);
        beforComponents.add(lhdBetConfirmButton);
        beforComponents.add(lhdCancelButton);
        componentMap.setLhdBetkuangImage(lhdBetkuangImage);
        componentMap.setLhdBetMessageTest(lhdBetMessageTest);
        componentMap.setLhdBetConfirmButton(lhdBetConfirmButton);
        componentMap.setLhdCancelButton(lhdCancelButton);
        componentMap.setDisable(1);
        userComponent.put(player.getUniqueId().toString(),componentMap);
        List<VexComponents> components = vexGui.getComponents();
        components.remove(componentMap.getLhdLotteryInformation());
        VexViewAPI.openGui(player,createLhdGui(player,beforComponents,components));
    }

    //刷新账户信息
    private static void refreshMoney(Player player){
        OpenedVexGui opg = VexViewAPI.getPlayerCurrentGui(player);
        //查询账户信息
        JettonImpl jettonImpl = JettonImpl.getJettonImpl();
        JettonEntity jettonEntity = jettonImpl.selectJettonById(player.getUniqueId().toString());
        Component component = MainGui.getUserComponent().get(player.getUniqueId().toString());
        VexText jindouText = component.getJindouText();
        VexText yindouText = component.getYindouText();
        opg.removeDynamicComponent(jindouText);
        opg.removeDynamicComponent(yindouText);
        List<String> jindouMessage = component.getJindouMessage();
        List<String> yindouMessage = component.getYindouMessage();
        jindouMessage.clear();
        yindouMessage.clear();
        jindouMessage.add("§e金豆: §6"+jettonEntity.getJettonB());
        yindouMessage.add("§3银豆: §6"+jettonEntity.getJettonA());
        YamlConfiguration base_jindounum = VvGuiYml.getBase_jindounum();
        YamlConfiguration base_yindounum = VvGuiYml.getBase_yindounum();
        jindouText = new VexText(base_jindounum.getInt("x"), base_jindounum.getInt("y"),jindouMessage,1.0);
        yindouText = new VexText(base_yindounum.getInt("x"), base_yindounum.getInt("y"),yindouMessage,1.0);
        opg.addDynamicComponent(jindouText);
        opg.addDynamicComponent(yindouText);
        component.setJindouText(jindouText);
        component.setYindouText(yindouText);
        MainGui.getUserComponent().put(player.getUniqueId().toString(),component);

    }

    /**
     * 进行押注
     * @param player 玩家
     * @param betType 押注类型
     * @param sum 押注银豆
     */
    private static void lhdBetOn(Player player, String betType, VexTextField sum) {
        String amountStr = sum.getTypedText();
        OpenedVexGui opg = VexViewAPI.getPlayerCurrentGui(player);
        Integer amount;
        Component component = MainGui.getUserComponent().get(player.getUniqueId().toString());
        List<String> errorMessages = component.getErrorMessages();
        errorMessages.clear();
        YamlConfiguration systemConfig = Tavern.getSystemConfig();
        boolean antiAddictSystem = systemConfig.getBoolean("antiAddictSystem.open", true);
        //防沉迷验证
        if (antiAddictSystem) {
            Integer maxnum = systemConfig.getInt("antiAddictSystem.lhd", 20);
            UserConfigEntity userConfigEntity = ConfigDatabaseHandler.selectUserConfigDataNum(player.getUniqueId().toString());
            Integer timeLhd = userConfigEntity.getTimeLhd();
            if (timeLhd.equals(maxnum)) {
                errorMessages.add("§c§l今日押注次数已达上限");
                errorMessages.add("§c§l请合理安排游戏时间");
                sendMessage(player,opg, errorMessages);
                return;
            }
        }
        try {
            amount = Integer.valueOf(amountStr);
        } catch (Exception e) {
            errorMessages.add(MessageVv.gameLhdMust_integer);
            sendMessage(player,opg, errorMessages);
            return;
        }
        String maxnum = systemConfig.getString("game.lhd.maxnum", "99999");
        String minnum = systemConfig.getString("game.lhd.minnum", "100");
        Integer maxnumValue = Integer.valueOf(maxnum);
        Integer minnumValue = Integer.valueOf(minnum);
        if (amount.compareTo(maxnumValue) > 0) {
            errorMessages.add(MessageVv.gameLhdTo_long.replace("%long%", maxnumValue.toString()));
            sendMessage(player,opg, errorMessages);
            return;
        }
        if (amount.compareTo(minnumValue) < 0) {
            errorMessages.add(MessageVv.gameLhdTo_small.replace("%small%", minnumValue.toString()));
            sendMessage(player,opg, errorMessages);
            return;
        }

        if (amount < 1) {
            errorMessages.add(MessageVv.gameLhdSum_error);
            sendMessage(player,opg, errorMessages);
            return;
        }

        if (amount < 100) {
            errorMessages.add(MessageVv.gameLhdSum_error);
            sendMessage(player,opg, errorMessages);
            return;
        }
        //判断是否有足够的金豆
        JettonImpl jettonImpl = JettonImpl.getJettonImpl();
        JettonEntity jettonEntity = jettonImpl.selectJettonById(player.getUniqueId().toString());
        BigDecimal jettonB = jettonEntity.getJettonB();
        if (jettonB.compareTo(new BigDecimal(amount)) < 0) {
            errorMessages.add(MessageVv.gameLhdSum_not_enough);
            sendMessage(player,opg, errorMessages);
            return;
        }
        StringBuffer time = selectLhdTime();
        if (time == null) {
            errorMessages.add(MessageVv.gameLhdWait);
            sendMessage(player,opg, errorMessages);
            return;
        }
        bet(player,betType, amount,jettonImpl,jettonB);

    }

    private static void betOn(Player player, Integer amount, JettonImpl jettonImpl, String betType, BigDecimal jettonB){
        //扣除金豆
        JettonEntity jettonEntity1 = new JettonEntity();
        jettonEntity1.setUid(player.getUniqueId().toString());
        jettonEntity1.setJettonB(jettonB.subtract(new BigDecimal(amount)));
        jettonImpl.updateJettonById(jettonEntity1);
        PlayerBetEntity playerBetEntity = new PlayerBetEntity();
        playerBetEntity.setPlayerName(player.getName());
        playerBetEntity.setUid(player.getUniqueId().toString());
        playerBetEntity.setBetAmount(amount.doubleValue());
        playerBetEntity.setGameType("萌之争");
        playerBetEntity.setBetType(betType);
        BetDatabaseHandler.insert(playerBetEntity);
        player.sendMessage(Message.gameLhdBet_succeed);
    }

    private static void sendMessage(Player player, OpenedVexGui opg, List<String> messages){
        //错误提示框
        YamlConfiguration errorKuang = VvGuiYml.getLhdBetKuang();
        VexImage errorKuangImage = new VexImage(ImageUrlEnum.LHD_BETKUANG.getUrl(), errorKuang.getInt("x"), errorKuang.getInt("y"), errorKuang.getInt("width"), errorKuang.getInt("high"));
        YamlConfiguration lhdError = VvGuiYml.getLhdError();
        VexImage lhdErrorImage = new VexImage(ImageUrlEnum.LHD_ERROR.getUrl(), lhdError.getInt("x"), lhdError.getInt("y"), lhdError.getInt("width"), lhdError.getInt("high"));
        //提示语
        YamlConfiguration lhdErrorMessage = VvGuiYml.getLhdErrorMessage();
        VexText vexText = new VexText(lhdErrorMessage.getInt("x"), lhdErrorMessage.getInt("y"), messages,1);
        //确认按钮
        YamlConfiguration lhdConfirm = VvGuiYml.getLhdConfirm();
        VexButtonEx lhdConfirmButton = new VexButtonEx("lhdConfirmButton", "", ImageUrlEnum.LHD_CONFIRM.getUrl(), PitchImageUrlEnum.LHD_CONFIRM.getUrl(), lhdConfirm.getInt("x"),lhdConfirm.getInt("y"), lhdConfirm.getInt("width"), lhdConfirm.getInt("high"),new ButtonFunction(){
            @Override
            public void run(Player player) {
                //关闭当前选框
                Component componentMap = MainGui.getUserComponent().get(player.getUniqueId().toString());
                List<DynamicComponent> vexErrorComponents = componentMap.getVexErrorComponents();
                OpenedVexGui opg = VexViewAPI.getPlayerCurrentGui(player);
                componentMap.setDisable(0);
                MainGui.getUserComponent().put(player.getUniqueId().toString(),componentMap);
                for (DynamicComponent vexErrorComponent : vexErrorComponents) {
                    opg.removeDynamicComponent(vexErrorComponent);
                }
                opg.addDynamicComponent(componentMap.getLhdLotteryInformation());
            }
        });
        List<DynamicComponent> vexComponents = new ArrayList<>();
        List<VexComponents> beforComponents = new ArrayList<>();
        beforComponents.add(lhdErrorImage);
        beforComponents.add(errorKuangImage);
        beforComponents.add(vexText);
        beforComponents.add(lhdConfirmButton);
        VexGui vexGui = opg.getVexGui();
        List<VexComponents> components = vexGui.getComponents();
        vexComponents.add(lhdErrorImage);
        vexComponents.add(errorKuangImage);
        vexComponents.add(vexText);
        vexComponents.add(lhdConfirmButton);
        Map<String, Component> userComponent = MainGui.getUserComponent();
        Component componentMap = userComponent.get(player.getUniqueId().toString());
        components.remove(componentMap.getLhdLotteryInformation());
        componentMap.setVexErrorComponents(vexComponents);
        componentMap.setDisable(1);
        userComponent.put(player.getUniqueId().toString(), componentMap);
        VexViewAPI.openGui(player,createLhdGui(player,beforComponents,components));
    }

    //创建VV组件
    public static VexGui createLhdGui(Player p, List<VexComponents> beforComponents, List<VexComponents> components){
        VexGui gui = getGui();
        gui.addAllComponents(beforComponents);
        gui.addAllComponents(components);
        return gui;
    }
}
