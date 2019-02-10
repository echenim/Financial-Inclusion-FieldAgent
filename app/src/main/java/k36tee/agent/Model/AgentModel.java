package k36tee.agent.Model;

/**
 * Created by myron echenim  on 8/16/16.
 */
public class AgentModel {
    String id, name, email, trasaction, description, trasactionTime;
    long amount;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTrasaction() {
        return this.trasaction;
    }

    public void setTrasaction(String trasaction) {
        this.trasaction = trasaction;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTrasactionTime() {
        return this.trasactionTime;
    }

    public void setTrasactionTime(String trasactionTime) {
        this.trasactionTime = trasactionTime;
    }

    public long getAmount() {
        return this.amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
