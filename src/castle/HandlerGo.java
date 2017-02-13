package castle;

public class HandlerGo extends Handler {
	public HandlerGo(Game game) {
		super(game);
	}

	@Override
	public void doCmd(String direction) {
		game.goRoom(direction);
	}

}
