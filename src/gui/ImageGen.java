package gui;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class ImageGen {
	
	
	private int imgWidth, imgHeight, xFrameNum, yFrameNum;
	private int widthSize, heightSize;
	ArrayList<BufferedImage> animation = new ArrayList<>();
	BufferedImage tempImg;
	private String path;
	
	
	/*imgWidth/imgHeight = Image 4x4 imgWidth = width/2    path - image resource path	*/
	public ImageGen(int xFrameNum, int yFrameNum, String path, int widthSize, int heightSize){
		this.xFrameNum = xFrameNum;
		this.yFrameNum = yFrameNum;
		this.path = path;
		this.widthSize = widthSize;
		this.heightSize = heightSize;
		
		tempImg = null;
		
		try{tempImg = ImageIO.read(this.getClass().getResourceAsStream(path));}
		catch(IOException e){e.printStackTrace();}
		
		this.imgWidth = tempImg.getWidth()/xFrameNum;
		this.imgHeight = tempImg.getHeight()/yFrameNum;
		
		for(int i=1; i<=getFrameNumber(); i++) {
        	animation.add(getFrameByNumber(i));
        }
	}

	/*xImgCoordinate - choose picture    */
	public BufferedImage getFrameByCoords(int xFrameCoordinate, int yFrameCoordinate) {
		BufferedImage tempImage;
		BufferedImage endImage;
		int[] pixelImage;
		
		pixelImage = tempImg.getRGB((imgWidth*(xFrameCoordinate-1)), (imgHeight*(yFrameCoordinate-1)), imgWidth, imgHeight, null, 0, imgWidth);
		
		tempImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final int[] a = ( (DataBufferInt) tempImage.getRaster().getDataBuffer() ).getData();
		System.arraycopy(pixelImage, 0, a, 0, pixelImage.length);
		
		endImage = new BufferedImage(widthSize, heightSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = endImage.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.drawImage(tempImage, 0, 0, widthSize, heightSize, 0, 0, tempImage.getWidth(), tempImage.getHeight(), null);
		g.dispose();
		
		return endImage;
	}
	
	public BufferedImage getFrameByNumber(int frameNumber) {
		BufferedImage tempImage;
		BufferedImage endImage;
		int[] pixelImage;
		
		//Failsafe
		if(frameNumber > (xFrameNum*yFrameNum)) {
			System.out.println("Frame: " + frameNumber + " out of index.");
			return null;
		}
		
		int yFrameCoordinate = (int) Math.ceil(Double.valueOf(frameNumber)/Double.valueOf(xFrameNum));
		int xFrameCoordinate = frameNumber - (yFrameCoordinate-1)*xFrameNum;

		pixelImage = tempImg.getRGB((imgWidth*(xFrameCoordinate-1)), (imgHeight*(yFrameCoordinate-1)), imgWidth, imgHeight, null, 0, imgWidth);
		
		tempImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final int[] a = ( (DataBufferInt) tempImage.getRaster().getDataBuffer() ).getData();
		System.arraycopy(pixelImage, 0, a, 0, pixelImage.length);
		
		endImage = new BufferedImage(widthSize, heightSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = endImage.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.drawImage(tempImage, 0, 0, widthSize, heightSize, 0, 0, tempImage.getWidth(), tempImage.getHeight(), null);
		g.dispose();
		
		return endImage;
	}
	
	
	public ArrayList<BufferedImage> getFrameArray(){
		return animation;
	}
	
	public int getFrameNumber() {
	return xFrameNum*yFrameNum;
	}
	
	
	
	
	
	
	
}
