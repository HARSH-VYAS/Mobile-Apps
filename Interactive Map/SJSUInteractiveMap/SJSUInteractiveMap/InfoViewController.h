//
//  InfoViewController.h
//  SJSUInteractiveMap
//
//  Created by Harsh Vyas on 11/11/15.
//  Copyright © 2015 SJSUTeam9. All rights reserved.
//

#import <UIKit/UIKit.h>

int _happiness;

@interface InfoViewController : UIViewController
{
  IBOutlet  UILabel *name;
  IBOutlet  UITextView *address;
  IBOutlet UILabel  *distance1;
  IBOutlet UILabel  *duration1;
}
@property (weak, nonatomic) IBOutlet UIImageView *imageView;

-(void)setHappiness:(int)method distance:(NSString *)distance  duration:(NSString *)duration ;


@end
