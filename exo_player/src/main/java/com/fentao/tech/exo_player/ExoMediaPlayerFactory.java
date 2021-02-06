package com.fentao.tech.exo_player;

import android.content.Context;

import com.fentao.tech.player_java.player.PlayerFactory;

/**
 * @ProjectName: WWTPlayer
 * @Package: com.fentao.tech.exo_player
 * @ClassName: ExoMediaPlayerFactory
 * @Description: java类作用描述
 * @Author: allen
 * @CreateDate: 2021/2/6 10:00
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/6 10:00
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ExoMediaPlayerFactory extends PlayerFactory<ExoMediaPlayer> {

    @Override
    public ExoMediaPlayer createPlayer(Context context) {
        return new ExoMediaPlayer(context);
    }
}
