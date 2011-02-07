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
using System.Collections.Generic;
using System.Linq.Expressions;
using System.ComponentModel;
using System.Runtime.Serialization;
using System.Diagnostics;
using AccessingWP7Devices.Assets.Model;

namespace AccessingWP7Devices.Assets.Model
{
    [DataContract]
    public abstract class NotifyingObject : INotifyPropertyChanged
    {
        #region Fields

        private Dictionary<string, object> _values = new Dictionary<string, object>();

        [IgnoreDataMember]
        private Dictionary<string, object> Values
        {
            get
            {
                if (_values == null)
                {
                    _values = new Dictionary<string, object>();
                }

                return _values;
            }
        }

        #endregion

        #region Protected

        protected void SetValue<T>(Expression<Func<T>> propertySelector, T value)
        {
            string propertyName = GetPropertyName(propertySelector);

            Values[propertyName] = value;
            NotifyPropertyChanged(propertySelector);
        }

        protected void SetValue<T>(string propertyName, T value)
        {
            Values[propertyName] = value;
            NotifyPropertyChanged(propertyName);
        }

        protected T GetValue<T>(Expression<Func<T>> propertySelector)
        {
            string propertyName = GetPropertyName(propertySelector);

            return GetValue<T>(propertyName);
        }

        protected T GetValue<T>(string propertyName)
        {
            object value;
            if (Values.TryGetValue(propertyName, out value))
            {
                return (T)value;
            }

            return default(T);
        }

        protected void NotifyPropertyChanged<T>(Expression<Func<T>> propertySelector)
        {
            var propertyChanged = PropertyChanged;
            if (propertyChanged != null)
            {
                string propertyName = GetPropertyName(propertySelector);
                propertyChanged(this, new PropertyChangedEventArgs(propertyName));
            }
        }

        #endregion

        #region INotifyPropertyChanged Members

        /// <summary>
        /// Raised when a property on this object has a new value.
        /// </summary>
        public event PropertyChangedEventHandler PropertyChanged;

        /// <summary>
        /// Raises this object's PropertyChanged event.
        /// </summary>
        /// <param name="propertyName">The property that has a new value.</param>
        protected void NotifyPropertyChanged(string propertyName)
        {
            this.VerifyPropertyName(propertyName);

            PropertyChangedEventHandler handler = this.PropertyChanged;
            if (handler != null)
            {
                var e = new PropertyChangedEventArgs(propertyName);
                handler(this, e);
            }
        }

        #endregion // INotifyPropertyChanged Members

        #region Privates

        private string GetPropertyName(LambdaExpression expression)
        {
            var memberExpression = expression.Body as MemberExpression;
            if (memberExpression == null)
            {
                throw new InvalidOperationException();
            }

            return memberExpression.Member.Name;
        }

        #endregion

        #region Debugging

        /// <summary>
        /// Warns the developer if this object does not have
        /// a public property with the specified name. This 
        /// method does not exist in a Release build.
        /// </summary>
        [Conditional("DEBUG")]
        [DebuggerStepThrough]
        public void VerifyPropertyName(string propertyName)
        {
            // Verify that the property name matches a real,  
            // public, instance property on this object.
            if (this.GetType().GetProperty(propertyName) == null)
            {
                string msg = "Invalid property name: " + propertyName;

                if (this.ThrowOnInvalidPropertyName)
                    throw new Exception(msg);
                else
                    Debug.Assert(false, msg);
            }
        }

        /// <summary>
        /// Returns whether an exception is thrown, or if a Debug.Fail() is used
        /// when an invalid property name is passed to the VerifyPropertyName method.
        /// The default value is false, but subclasses used by unit tests might 
        /// override this property's getter to return true.
        /// </summary>
        protected virtual bool ThrowOnInvalidPropertyName { get; private set; }

        #endregion // Debugging Aides
    }
}
