package wang.dreamland.www.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*角色用户中间表*/
public class RoleUser {
    @Id//标识主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) //自增长策略
    private Long id;

    private Long uId;

    private Long rId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public Long getrId() {
        return rId;
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }
}