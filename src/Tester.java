/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Zilia_000
 */
public class Tester {
    public static void main(String[] args){
        SCUtility test=new SCUtility();
        
        SeamCarver sc=new SeamCarver(new Picture("3x7.png"));
        System.out.printf("Printing energy calculated for each pixel.\n");        

        for (int j = 0; j < sc.height(); j++)
        {
            for (int i = 0; i < sc.width(); i++)
                System.out.printf("%9.0f ", sc.energy(i, j));

            System.out.println();
        }
        sc.removeHorizontalSeam(sc.findHorizontalSeam());
        for(int i=0;i<sc.picHeight;i++){
            for(int j=0;j<sc.picWidth;j++){
                System.out.print(sc.energyArray[i][j]+" ");
            }
            System.out.println();
        }
        sc.removeVerticalSeam(sc.findVerticalSeam());
        for(int i=0;i<sc.picHeight;i++){
            for(int j=0;j<sc.picWidth;j++){
                System.out.print(sc.energyArray[i][j]+" ");
            }
            System.out.println();
        }
    }
    private static void printHorizontalSeam(SeamCarver sc)
    {        
        double totalSeamEnergy = 0;

        int[] horizontalSeam = sc.findHorizontalSeam();
        for (int j = 0; j < sc.height(); j++)
        {
            for (int i = 0; i < sc.width(); i++)            
            {
                char lMarker = ' ';
                char rMarker = ' ';
                if (j == horizontalSeam[i])
                {
                    lMarker = '[';
                    rMarker = ']';
                    totalSeamEnergy += sc.energy(i, j);
                }

                System.out.printf("%c%6.0f%c ", lMarker, sc.energy(i, j), rMarker);
            }
            System.out.println();
        }                
        
        System.out.printf("\nTotal energy: %.0f\n\n", totalSeamEnergy);
    }


    private static void printVerticalSeam(SeamCarver sc)
    {
        double totalSeamEnergy = 0;

        int[] verticalSeam = sc.findVerticalSeam();
        for (int j = 0; j < sc.height(); j++)
        {
            for (int i = 0; i < sc.width(); i++)            
            {
                char lMarker = ' ';
                char rMarker = ' ';
                if (i == verticalSeam[j])
                {
                    lMarker = '[';
                    rMarker = ']';
                    totalSeamEnergy += sc.energy(i, j);
                }

                System.out.printf("%c%6.0f%c ", lMarker, sc.energy(i, j), rMarker);
            }

            System.out.println();
        }                
        
        System.out.printf("\nTotal energy: %.0f\n\n", totalSeamEnergy);
    }
}
