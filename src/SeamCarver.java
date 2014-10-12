
import java.awt.Color;
/**
 *
 * @author Ziliang Wang
 */
public class SeamCarver {

    Picture original;
    int picHeight;
    int picWidth;
    int[][] energyArray;

    public SeamCarver(Picture picture) {
        original = picture;
        picWidth = original.width();
        picHeight = original.height();
        energyArray=new int[picHeight][picWidth];
    }

    /**
     * Gets the original picture
     * @return the priginal picture
     */
    public Picture picture() {
        return original;
    }

    /**
     * Gets the width of the original picture
     * @return the width of the original picture
     */
    public int width() {
        return original.width();
    }

    /**
     * Gets the height of the original picture
     * @return the height of the original picture
     */
    public int height() {
        return original.height();
    }

    /**
     * Calculates the energy at (x,y)
     * @param x x point
     * @param y y poiny
     * @return the energy at (x,y)
     */
    public double energy(int x, int y) {
        if (x == 0 || y == 0 || x >= (picWidth - 1) || y >= (picHeight - 1)) {
            return 195075;
        } else {
            Color upper = original.get(x, y - 1);
            Color lower = original.get(x, y + 1);
            Color left = original.get(x - 1, y);
            Color right = original.get(x + 1, y);
            double xEnergy = Math.pow(upper.getRed() - lower.getRed(), 2) + Math.pow(upper.getBlue() - lower.getBlue(), 2) + Math.pow(upper.getGreen() - lower.getGreen(), 2);
            double yEnergy = Math.pow(left.getRed() - right.getRed(), 2) + Math.pow(left.getBlue() - right.getBlue(), 2) + Math.pow(left.getGreen() - right.getGreen(), 2);
            return xEnergy + yEnergy;
        }
    }

    /**
     * Gets the horizontal seam with min energy
     * @return the horizontal seam with min energy
     */
    public int[] findHorizontalSeam() {
        double[] horizontalEnergy = new double[picHeight];
        for (int i = 0; i < picHeight; i++) {
            double tmpEnergy = 0;
            for (int j = 0; j < picWidth; j++) {
                tmpEnergy += this.energy(j, i);
                energyArray[i][j] = (int) this.energy(j, i);
            }
            horizontalEnergy[i] = tmpEnergy;
        }
        double min = Double.POSITIVE_INFINITY;
        int index = -1;
        for (int i = 0; i < picHeight; i++) {
            if (horizontalEnergy[i] < min) {
                min = horizontalEnergy[i];
                index = i;
            }
        }
        return energyArray[index];
    }

    /**
     * Gets the vertical seam with min energy
     * @return the vertical seam with min energy
     */
    public int[] findVerticalSeam() {
        double[] verticalEnergy = new double[picWidth];
        for (int i = 0; i < picWidth; i++) {
            double tmpEnergy = 0;
            for (int j = 0; j < picHeight; j++) {
                tmpEnergy += this.energy(i, j);
                energyArray[j][i] = (int) this.energy(i, j);
            }
            verticalEnergy[i] = tmpEnergy;
        }
        double min = Double.POSITIVE_INFINITY;
        int index = -1;
        for (int i = 0; i < picWidth; i++) {
            if (verticalEnergy[i] < min) {
                min = verticalEnergy[i];
                index = i;
            }
        }
        int[] colArray = new int[picHeight];
        for (int i = 0; i < picHeight; i++) {
            colArray[i] = energyArray[i][index];
        }
        return colArray;
    }
    
    /**
     * Remove the vertical seam with min energy
     * @param a the vertical seam with min energy
     */
    public void removeVerticalSeam(int[] a){
        int index=-1;
        for(int i=0;i<picWidth;i++){
            int matchFlag=0;
            if(a[0]==energyArray[0][i]){
                for(int j=0;j<picHeight;j++){
                    if(a[j]!=energyArray[j][i]){
                        matchFlag=0;
                        break;
                    }else{
                        matchFlag++;
                    }
                }
                if(matchFlag==picHeight){
                    index=i;
                    break;
                }
            }
        }
        for(int j=0;j<picHeight;j++){
            for(int i=index;i<picWidth-1;i++){
                original.set(i, j, original.get(i+1, j));
                energyArray[j][i]=energyArray[j][i+1];
            }
        }
        for(int j=0;j<picHeight;j++){
            original.set(picWidth-1,j,new Color(255,255,255));
        }
        picWidth--;
    }
    
    /**
     * Remove the horizontal seam with min energy
     * @param a the horizontal seam with min energy
     */
    public void removeHorizontalSeam(int[] a){
        int index=-1;
        int matchFlag=0;
        for(int i=0;i<picHeight;i++){
            if(a[0]==energyArray[i][0]){
                for(int j=0;j<picWidth;j++){
                    if(a[j]!=energyArray[i][j]){
                        matchFlag=0;
                        break;
                    }else{
                        matchFlag++;
                    }
                }
                if(matchFlag==picWidth){
                    index=i;
                    break;
                }
        }
        }
        for(int i=index;i<picHeight-1;i++){
            for(int j=0;j<picWidth;j++){
                original.set(j, i, original.get(j, i+1));
                energyArray[i][j]=energyArray[i+1][j];
            }
        }
        for(int i=0;i<picWidth;i++){
            original.set(i,picHeight-1,new Color(255,255,255));
        }
        picHeight--;
    }
}
