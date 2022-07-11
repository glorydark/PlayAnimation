package glorydark.playanimation;

import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.ProtocolInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author iGxnon
 * @date 2021/8/30
 */
@Getter
@Setter
public class AnimateEntityPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.ANIMATE_ENTITY_PACKET;

    /**
     * Expression to check if the animation needs to stop.
     *
     * @param stopExpression molang expression (???)
     * @return molang expression (???)
     */
    private String animation;
    /**
     * The entity state to move to when the animation has finished playing.
     *
     * @param nextState state after animation has finished
     * @return state after animation has finished
     */
    private String nextState;
    /**
     * Expression to check if the animation needs to stop.
     *
     * @param stopExpression molang expression (???)
     * @return molang expression (???)
     */
    private String stopExpression = "";
    /**
     * Name of the animation controller to use.
     *
     * @param controller controller name
     * @return controller name
     */
    private String controller = "query.any_animation_finished";
    /**
     * Time taken to blend out of the specified animation.
     *
     * @param blendOutTime time
     * @return time
     */
    private float blendOutTime = 0;
    /**
     * Entity runtime IDs to run the animation on when sent to the client.
     *
     * @param runtimeEntityIds runtime entity IDs list
     * @return runtime entity IDs list
     */
    private Set<Long> entityRuntimeIDs = new HashSet<>();

    private int stopExpressionVersion = 0; // 需要这个才能正常播放！

    // 客户端一般不会发这个包
    @Override
    public void decode() {
        this.animation = this.getString();
        this.nextState = this.getString();
        this.stopExpression = this.getString();
        this.controller = this.getString();
        this.blendOutTime = this.getLFloat();
        for (int i = 0, len = (int) this.getUnsignedVarInt(); i < len; i++) {
            this.entityRuntimeIDs.add(this.getEntityRuntimeId());
        }
    }

    @Override
    public void encode() {
        this.reset();
        this.putString(this.animation);
        this.putString(this.nextState);
        this.putString(this.stopExpression);
        this.putLInt(this.stopExpressionVersion); //Added (1.17.40版本以上需要这个包，帮原作者加上)
        this.putString(this.controller);
        this.putLFloat(this.blendOutTime);
        this.putUnsignedVarInt(this.entityRuntimeIDs.size());
        for (long entityRuntimeId : this.entityRuntimeIDs){
            this.putEntityRuntimeId(entityRuntimeId);
        }
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
