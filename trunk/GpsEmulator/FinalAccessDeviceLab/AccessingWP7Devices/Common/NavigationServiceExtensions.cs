using System;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Documents;
using System.Windows.Ink;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Shapes;
using System.Windows.Navigation;
using Microsoft.Phone.Controls;

namespace AccessingWP7Devices.Common
{
    internal static class NavigationServiceExtensions
    {
        public static bool Navigate<TPage>(this NavigationService navigationService)
            where TPage : PhoneApplicationPage
        {
            string pageUriString = string.Format("/Pages/{0}.xaml", typeof(TPage).Name);
            return navigationService.Navigate(new Uri(pageUriString, UriKind.Relative));
        }        
    }
}
