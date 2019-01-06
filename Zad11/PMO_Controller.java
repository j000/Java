import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class PMO_Controller implements ControllerInterface {
	private BasketControlInterface basket;
	private GameInterface game;

	private class GUI extends JFrame {
		private static final long serialVersionUID = -6810437337842095871L;
		private JButton left = new JButton("Lewo");
		private JButton right = new JButton("Prawo");

		public GUI()
		{
			setSize(new Dimension(150, 150));
			add(BorderLayout.WEST, left);
			add(BorderLayout.EAST, right);
			setTitle("Kontroler do gry");
			setVisible(true);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e)
				{
				}

				@Override
				public void keyPressed(KeyEvent e)
				{
				}

				@Override
				public void keyReleased(KeyEvent e)
				{
					if (e.getKeyCode() == 37) {
						basket.moveLeft();
					}
					if (e.getKeyCode() == 39) {
						basket.moveRight();
					}
				}
			});
			setFocusable(true);

			left.addActionListener((a) -> { basket.moveLeft(); });
			right.addActionListener((a) -> { basket.moveRight(); });
		}
	}

	@Override
	public void setGame(GameInterface gi)
	{
		game = gi;
		basket = gi.getController();
	}

	public PMO_Controller()
	{
		SwingUtilities.invokeLater(() -> { new GUI(); });
	}
}
