package com.erstiwoche.uiElements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.erstiwoche.Main;
import com.erstiwoche.ResourceLoader;
import com.erstiwoche.Inputs.Button;

public class GUIButton extends Button {

	static BitmapFont font = new BitmapFont();
	static {
		font.setColor(Color.BLACK);
	}

	boolean hoverd;
	boolean change;

	public float centerPercentX;
	public float centerPercentY;
	public float percentWidth;
	public float percentHeight;

	public String label;
	public String texture;

	public boolean onHoverBigger = false;

	public GUIButton(String label, String buttonName, GUIButton buttonToTakeSize) {
		this(label, buttonName, buttonToTakeSize.centerPercentX, buttonToTakeSize.centerPercentY,
				buttonToTakeSize.percentWidth, buttonToTakeSize.percentHeight);
	}

	public GUIButton(String label, String buttonName, float centerPercentX, float centerPercentY, float percentWidth,
			float percentHeight) {
		this.label = label;
		this.centerPercentX = centerPercentX;
		this.centerPercentY = centerPercentY;
		this.percentWidth = percentWidth;
		this.percentHeight = percentHeight;

		this.texture = buttonName;

		if (texture != null) {
			ResourceLoader.getInstance().addToLoad(buttonName);
		}

		this.hoverd = false;
		this.change = false;
	}

	public GUIButton setOnHoverBigger(boolean bigger) {
		this.onHoverBigger = bigger;
		return this;
	}

	public void pressAt(int x, int y) {
		this.release();
		if (centerPercentX - percentWidth / 2 < x && x < centerPercentX + percentWidth / 2) {
			if (centerPercentY - percentHeight / 2 < y && y < centerPercentY + percentHeight / 2) {
				this.press();
			}
		}
	}

	public void setHovered(boolean hoverd) {
		this.hoverd = hoverd;
	}
	
	public void setChange(boolean change) {
		this.change = change;
	}

	public static float hoverSize = 5f;

	public void render(SpriteBatch batch) {
		float xpos = (Main.getInstance().getWidth() * centerPercentX / 100);
		float ypos = (Main.getInstance().getHeight() * centerPercentY / 100);
		float width = ((float) Main.getInstance().getWidth() * percentWidth / 100);
		float height = ((float) Main.getInstance().getHeight() * percentHeight / 100);

		if (texture != null) {
			Texture button = ResourceLoader.getInstance().getButton(texture);
			if(change){
				button = ResourceLoader.getInstance().getButton("new");
			}

			float percentWidth = this.percentWidth;
			float percentHeight = this.percentHeight;

			if (this.hoverd) {
				if (onHoverBigger) {
					percentWidth += hoverSize;
					percentHeight += hoverSize;
				}
			}

			width = ((float) Main.getInstance().getWidth() * percentWidth / 100);
			height = ((float) Main.getInstance().getHeight() * percentHeight / 100);

			float buttonRatioAspect = height / width;
			float textureRatioAspect = ((float) button.getHeight()) / ((float) button.getWidth());

			if (textureRatioAspect > buttonRatioAspect) { // h�her als breit ==>
															// h�he als max
				width = height / textureRatioAspect;
			} else { // breiter als hoch
				height = width * textureRatioAspect;
			}

			batch.draw(button, xpos - (width / 2), ypos - (height / 2), width, height);
		}

		drawLabel(batch);

	}

	public void drawLabel(SpriteBatch batch) {
		float xpos = (Main.getInstance().getWidth() * centerPercentX / 100);
		float ypos = (Main.getInstance().getHeight() * centerPercentY / 100);

		String label = recalcLabelIfTooLong();
		float labelWidth = font.getBounds(label).width;
		float labelHeight = font.getBounds(label).height;

		if (this.hoverd) {
			font.setColor(Color.RED);
		}

		HAlignment alignment = HAlignment.CENTER;

		String labelCopy = label;
		int index = labelCopy.indexOf("\n");
		int newLines = 0;
		while (index != -1) {
			newLines++;
			labelCopy = labelCopy.substring(index + 1);
			index = labelCopy.indexOf("\n");
		}

		float yoffset = (labelHeight / 2);
		
		font.drawMultiLine(batch, label, xpos, ypos + yoffset+ newLines * labelHeight, 0, alignment);
		// wird
		// nach
		// unten
		// rechts
		// gezeichnet
		font.setColor(Color.BLACK);
	}

	private String recalcLabelIfTooLong() {
		String label = this.label;
		float width = ((float) Main.getInstance().getWidth() * percentWidth / 100);
		float height = ((float) Main.getInstance().getHeight() * percentHeight / 100);

		float labelWidth = font.getBounds(label).width;
		float labelHeight = font.getBounds(label).height;

		String tooLong = "...";
		boolean toLong = labelWidth > width;

		if (toLong) {
			label = label + tooLong;
		}
		while (labelWidth > width) {
			label = label.substring(0, label.length() - 1 - (tooLong.length()));
			label = label + tooLong;

			labelWidth = font.getBounds(label).width;
			labelHeight = font.getBounds(label).height;
		}

		return label;
	}

}