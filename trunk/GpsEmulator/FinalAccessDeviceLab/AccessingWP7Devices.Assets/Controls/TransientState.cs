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
using Microsoft.Phone.Shell;

namespace AccessingWP7Devices.Assets.Controls
{
    public static class TransientState
    {
        public static void Set<T>(string key, T state)
        {
            PhoneApplicationService.Current.State[key] = state;
        }

        public static T Get<T>(string key, bool remove = true, T defaultValue = default(T))
        {
            object state;
            if (!PhoneApplicationService.Current.State.TryGetValue(key, out state))
            {
                state = defaultValue;
            }
            else
            {
                if (remove)
                {
                    PhoneApplicationService.Current.State.Remove(key);
                }
            }

            return (T)state;
        }        
    }
}
