package com.fentao.tech.player_java.player;

import android.content.Context;

/**
 * @ProjectName: WWTPlayer
 * @Package: com.fentao.tech.player_java
 * @ClassName: PlayerFactory
 * @Description: java类作用描述
 * @Author: allen
 * @CreateDate: 2021/2/6 10:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/6 10:04
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public abstract class PlayerFactory<P extends AbstractPlayer> {

    public abstract P createPlayer(Context context);

}
