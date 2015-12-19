//
//  InfoViewController.m
//  SJSUInteractiveMap
//
//  Created by Harsh Vyas on 11/11/15.
//  Copyright © 2015 SJSUTeam9. All rights reserved.
//

#import "InfoViewController.h"

@interface InfoViewController ()

@end

@implementation InfoViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    
}

-(void)setHappiness:(int)method distance:(NSString *)distance  duration:(NSString *)duration
{

    
    switch (method) {
        case 1:
            
            [self.imageView setImage:[UIImage imageNamed:@"MLK.jpg"]];
      
            [name setText:@"Kings Library"];
            [address setText:@"Dr. Martin Luther King, Jr. Library, 150 East San Fernando Street, San Jose, CA 95112"];
            [distance1 setText:distance];
            [duration1 setText:duration];
            
            break;
        case 2:
            
            [self.imageView setImage:[UIImage imageNamed:@"ENG.JPG"]];
            
            [name setText:@"Engineering Building"];
            [address setText:@"San José State University Charles W. Davidson College of Engineering, 1 Washington Square, San Jose, CA 95112"];
            [distance1 setText:distance];
            [duration1 setText:duration];

           
            break;
        case 3:
            
            [self.imageView setImage:[UIImage imageNamed:@"BBC.jpg"]];
            
            [name setText:@"Boccardo Business Center"];
            [address setText:@"Boccardo Business Complex, San Jose, CA 95112"];
            [distance1 setText:distance];
            [duration1 setText:duration];


            break;
        case 4:
            
            
            [self.imageView setImage:[UIImage imageNamed:@"YUH.jpg"]];
            
            [name setText:@"Yoshihiro Uchida Hall"];
            [address setText:@"Yoshihiro Uchida Hall, San Jose, CA 95112"];
            [distance1 setText:distance];
            [duration1 setText:duration];


            break;
        case 5:
            
            [self.imageView setImage:[UIImage imageNamed:@"SPG.jpeg"]];
            
            [name setText:@"South Parking Garage"];
            [address setText:@"Dr. Martin Luther King, Jr. Library, 150 East San Fernando Street, San Jose, CA 95112"];
            [distance1 setText:distance];
            [duration1 setText:duration];

   
            break;
        case 6:
            
            
            [self.imageView setImage:[UIImage imageNamed:@"SU.jpg"]];
            
            [name setText:@"Student Union"];
            [address setText:@"Student Union Building, San Jose, CA 95112"];
            [distance1 setText:distance];
            [duration1 setText:duration];

  
            break;
            
        default:
            
            [name setText:@"NA"];
            [address setText:@"NA"];
            [distance1 setText:@"NA"];
            [duration1 setText:@"NA"];

            
            break;
    }

}

@end
