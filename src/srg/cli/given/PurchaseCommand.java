package srg.cli.given;

public class PurchaseCommand extends ShipCommand {
    public final int amount;
    public final String item;


    public PurchaseCommand(String item, Integer amount) {
        super(CommandType.PURCHASE_ITEM, item);
        this.item = item;
        this.amount = amount;
    }

}
