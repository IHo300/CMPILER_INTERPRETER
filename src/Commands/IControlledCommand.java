package Commands;

public interface IControlledCommand extends ICommand{
    public abstract ControlTypeEnum getControlType();
    public abstract void addCommand(ICommand command);
}
