import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Spinner } from "@/components/ui/spinner";
import { Textarea } from "@/components/ui/textarea";
import { toast } from "@/components/ui/toast";
import { useCreateContactMutation } from "@/redux/api/contactSlice";
import { type CreateContactType } from "@/types/comman";
import { Mail, Phone, MapPin } from "lucide-react";
import { motion } from "motion/react";
import { useState } from "react";

function Contact() {
  const [contactData, setContactData] = useState<CreateContactType>({
    name: "",
    message: "",
    email: "",
  });
  const [create, { isLoading, error, data }] = useCreateContactMutation();
  const handleCreate = () => {
    if (
      contactData.email.trim() == "" ||
      contactData.name.trim() == "" ||
      contactData.message.trim() == "" ||
      contactData.message.length < 12
    ) {
      toast.add({
        title: "Invalid data",
        type: "error",
      });
      return;
    }
    create(contactData);
  };
  if (error)
    toast.add({
      title: "Invalid data",
      type: "error",
    });
  if (data)
    toast.add({
      title: data?.message,
      type: "success",
    });
  return (
    <section className="max-w-7xl mx-auto px-6 py-20 grid md:grid-cols-2 gap-12 items-start">
      <motion.div
        initial={{ opacity: 0, x: -30 }}
        whileInView={{ opacity: 1, x: 0 }}
        viewport={{ once: true }}
      >
        <h1 className="text-4xl font-bold mb-4">Get in Touch</h1>

        <p className="text-muted-foreground mb-8">
          Have a project, event, or collaboration in mind? Feel free to reach
          out. I’d love to connect with you.
        </p>

        <div className="space-y-4 text-sm">
          <div className="flex items-center gap-3">
            <Mail size={18} />
            vjsiddhuvlogsadv@gmail.com
          </div>
          <div className="flex items-center gap-3">
            <Phone size={18} />
            +91 7200571056
          </div>
          <div className="flex items-center gap-3">
            <MapPin size={18} />
            Tamil Nadu, India
          </div>
        </div>
      </motion.div>

      {/* RIGHT FORM */}
      <motion.div
        initial={{ opacity: 0, x: 30 }}
        whileInView={{ opacity: 1, x: 0 }}
        viewport={{ once: true }}
        className="border border-border rounded-2xl p-6 bg-background"
      >
        <form className="space-y-5">
          <div>
            <Label className="mb-2" htmlFor="name">
              Name
            </Label>
            <Input
              type="text"
              className="py-5"
              placeholder="Enter your name"
              name="name"
              required
              onChange={(e) =>
                setContactData((prev) => ({ ...prev, name: e.target.value }))
              }
            />
          </div>

          <div>
            <Label className="mb-2">Email</Label>
            <Input
              type="email"
              required
              className="py-5"
              placeholder="Enter your email"
              onChange={(e) =>
                setContactData((prev) => ({ ...prev, email: e.target.value }))
              }
            />
          </div>

          <div>
            <Label className="mb-2">Message</Label>
            <Textarea
              rows={4}
              required
              placeholder="Write your message..."
              onChange={(e) =>
                setContactData((prev) => ({ ...prev, message: e.target.value }))
              }
            />
          </div>

          <Button className="w-full" onClick={handleCreate}>
            {isLoading ? (
              <>
                Sending....
                <Spinner />
              </>
            ) : (
              "Send Message"
            )}
          </Button>
        </form>
      </motion.div>
    </section>
  );
}

export default Contact;
