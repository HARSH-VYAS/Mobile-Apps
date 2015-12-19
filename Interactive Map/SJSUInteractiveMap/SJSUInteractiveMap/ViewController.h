//
//  ViewController.h
//  SJSUInteractiveMap
//
//  Created by Harsh Vyas on 11/6/15.
//  Copyright (c) 2015 SJSUTeam9. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <CoreLocation/CoreLocation.h>

@interface ViewController : UIViewController <UICollectionViewDelegate, UISearchBarDelegate>
@property (retain, nonatomic) IBOutlet UIButton *buttonk;

@property (weak, nonatomic) IBOutlet UIImageView *imageView;
@property (weak, nonatomic) IBOutlet UIScrollView *scrollView;
@property (weak, nonatomic) IBOutlet UIView *contentView;
@property (weak, nonatomic) IBOutlet UISearchBar *uiSearchBar;

@property (nonatomic, retain) CLLocationManager *locationManager;

@end

